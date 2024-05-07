package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.LegitOldPassword;
import com.example.miracles_store.validator.annotation.UserIdExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@LegitOldPassword
public class PasswordChangeDto {

    @UserIdExists
    private Integer userId;

    @Size(max = 50)
    @NotBlank
    private String oldPassword;

    @Size(max = 50)
    @NotBlank
    private String newPassword;
}
