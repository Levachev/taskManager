package com.example.taskManager.service;

import com.example.taskManager.dto.JwtAuthenticationResponse;
import com.example.taskManager.dto.SignInRequest;
import com.example.taskManager.dto.SignUpRequest;
import com.example.taskManager.entity.User;
import com.example.taskManager.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authServiceDefaultImpl")
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        var newUser = userService.createUser(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, newUser.getId());
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
                )
        );

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

                var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, userService.getId(request.getEmail()));
    }
}
