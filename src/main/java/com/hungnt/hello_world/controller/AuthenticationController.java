package com.hungnt.hello_world.controller;

import com.hungnt.hello_world.dto.request.IntrospectRequest;
import com.hungnt.hello_world.dto.request.UserCreationRequest;
import com.hungnt.hello_world.dto.response.ApiResponse;
import com.hungnt.hello_world.dto.request.AuthenticationRequest;
import com.hungnt.hello_world.dto.response.AuthenticationResponse;
import com.hungnt.hello_world.dto.response.IntrospectResponse;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.service.AuthenticationService;
import com.hungnt.hello_world.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
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
    UserService userService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result =  authService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/register")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }
}
