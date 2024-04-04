package com.example.miracles_store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductCreateEditDto {

    @NotNull
    @Size(max = 250)
    private String name;

    @NotNull
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal cost;

    @NotNull
    private int productTypeId;
}
