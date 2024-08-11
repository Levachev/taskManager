package com.example.taskManager.service;

import com.example.taskManager.entity.User;
import com.example.taskManager.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found: "+email));
    }

    public UserDetailsService userDetailsService(){
        return this::findByEmail;
    }

    public User createUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public Long getId(String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found: "+email));
        return user.getId();
    }
}
