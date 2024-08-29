package com.hungnt.hello_world.controller;

import com.hungnt.hello_world.dto.request.IntrospectRequest;
import com.hungnt.hello_world.dto.response.ApiResponse;
import com.hungnt.hello_world.dto.request.AuthenticationRequest;
import com.hungnt.hello_world.dto.response.AuthenticationResponse;
import com.hungnt.hello_world.dto.response.IntrospectResponse;
import com.hungnt.hello_world.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean result =  authService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder().authenticated(result).build())
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }
}
