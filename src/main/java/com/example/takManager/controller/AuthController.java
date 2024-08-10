package com.example.takManager.controller;

import com.example.takManager.dto.JwtAuthenticationResponse;
import com.example.takManager.dto.SignInRequest;
import com.example.takManager.dto.SignUpRequest;
import com.example.takManager.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {
    private final AuthenticationService authenticationServiceImpl;

    @Operation(summary = "registration")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationServiceImpl.signUp(request);
    }

    @Operation(summary = "authorization")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationServiceImpl.signIn(request);
    }
}
