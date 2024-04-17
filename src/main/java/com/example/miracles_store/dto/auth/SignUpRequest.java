package com.example.miracles_store.dto.auth;

import com.example.miracles_store.validator.annotation.PasswordMatches;
import com.example.miracles_store.validator.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@PasswordMatches
public class SignUpRequest {

    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    @Size(min = 5, max = 250)
    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @Size(max = 50)
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
