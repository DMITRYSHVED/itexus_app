package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.UserIdExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotNull
    @UserIdExists
    private Integer id;

    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;
}
