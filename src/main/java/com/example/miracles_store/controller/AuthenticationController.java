package com.example.miracles_store.controller;

import com.example.miracles_store.dto.PasswordChangeDto;
import com.example.miracles_store.dto.auth.JwtAuthenticationResponse;
import com.example.miracles_store.dto.auth.SignInRequest;
import com.example.miracles_store.dto.auth.SignUpRequest;
import com.example.miracles_store.mapper.UserMapper;
import com.example.miracles_store.service.AuthenticationService;
import com.example.miracles_store.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication_controller")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        JwtAuthenticationResponse response = authenticationService.signUp(userMapper.signUpRequestDtoToEntity(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> update(@RequestBody @Valid PasswordChangeDto passwordChangeDto) {
        authenticationService.updatePassword(userMapper
                .passwordChangeDtoToEntity(passwordChangeDto, userService.getById(passwordChangeDto.getUserId())));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
