package com.example.miracles_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductTypeDto {

    private int id;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String name;
}
