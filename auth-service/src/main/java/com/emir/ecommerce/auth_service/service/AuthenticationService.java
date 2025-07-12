package com.emir.ecommerce.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emir.ecommerce.auth_service.domain.User;
import com.emir.ecommerce.auth_service.repository.UserRepository;
import com.emir.ecommerce.auth_service.dto.LoginRequestDTO;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));

        // JWT token üret
        return jwtService.generateToken(user);
    }
}
