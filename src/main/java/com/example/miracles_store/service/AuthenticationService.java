package com.example.miracles_store.service;

import com.example.miracles_store.dto.auth.JwtAuthenticationResponse;
import com.example.miracles_store.dto.auth.SignInRequest;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    public JwtAuthenticationResponse signUp(User user) {
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
    }
}
