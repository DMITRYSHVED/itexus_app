package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;
}
