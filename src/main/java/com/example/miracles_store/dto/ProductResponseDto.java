package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Integer id;

    private String name;

    private String description;

    private BigDecimal cost;

    private ProductTypeDto productType;

    private String encodedImage;
}
