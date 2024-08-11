package com.example.taskManager.service;

import com.example.taskManager.dto.JwtAuthenticationResponse;
import com.example.taskManager.dto.SignInRequest;
import com.example.taskManager.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
