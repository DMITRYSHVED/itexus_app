package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductReadDto {

    private int id;

    private String name;

    private String description;

    private BigDecimal cost;

    private String productTypeName;
}
