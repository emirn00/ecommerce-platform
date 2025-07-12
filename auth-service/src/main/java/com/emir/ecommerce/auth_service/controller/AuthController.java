package com.emir.ecommerce.auth_service.controller;

import com.emir.ecommerce.auth_service.dto.LoginRequestDTO;
import com.emir.ecommerce.auth_service.dto.RegisterRequestDTO;
import com.emir.ecommerce.auth_service.domain.User;
import com.emir.ecommerce.auth_service.repository.UserRepository;
import com.emir.ecommerce.auth_service.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public AuthController(UserRepository userRepository , AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "USER")
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String jwt = authenticationService.login(request);
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}

