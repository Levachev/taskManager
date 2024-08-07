package com.example.takManager.service;

import com.example.takManager.dto.JwtAuthenticationResponse;
import com.example.takManager.dto.SignInRequest;
import com.example.takManager.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
