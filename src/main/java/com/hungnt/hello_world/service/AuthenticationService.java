package com.hungnt.hello_world.service;

import com.hungnt.hello_world.dto.request.AuthenticationRequest;
import com.hungnt.hello_world.dto.request.IntrospectRequest;
import com.hungnt.hello_world.dto.response.AuthenticationResponse;
import com.hungnt.hello_world.dto.response.IntrospectResponse;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.exception.AppException;
import com.hungnt.hello_world.exception.ErrorCode;
import com.hungnt.hello_world.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder().valid(verified && expiryTime.after(new Date())).build();
    }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            var user = userRepository
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXSITS));

            boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

            var token = generateToken(user);

            return AuthenticationResponse.builder().token(token).authenticated(true).build();
        }

    private String generateToken (User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hungnt")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
