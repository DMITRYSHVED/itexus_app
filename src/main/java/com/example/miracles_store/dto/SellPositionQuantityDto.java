package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellPositionQuantityDto {

    private SellPositionResponseDto sellPositionResponseDto;

    private Integer quantity;
}
