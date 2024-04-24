package com.example.miracles_store.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @Size(min = 5, max = 250)
    @NotBlank
    @Email
    private String email;

    @Size(max = 50)
    @NotBlank
    private String password;
}
