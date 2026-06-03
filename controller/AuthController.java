package com.loginservice.loginservice.controller;

import com.loginservice.loginservice.dto.LoginRequest;
import com.loginservice.loginservice.entity.User;
import com.loginservice.loginservice.repository.UserRepository;
import com.loginservice.loginservice.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setPassword(
                encoder.encode(user.getPassword()));

        return repository.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        if (encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            String token = JwtUtil.generateToken(user.getEmail());

            Map<String, String> response = new HashMap<>();

            response.put("token", token);

            return response;
        }

        throw new RuntimeException("Credenciales incorrectas");
    }
}