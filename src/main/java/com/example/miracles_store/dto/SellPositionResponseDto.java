package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellPositionResponseDto {

    private Integer id;

    private ProductResponseDto product;

    private String size;

    private Integer quantity;

    private Boolean isActive;
}
