package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartResponseDto {

    private UserResponseDto user;

    private List<SellPositionQuantityDto> sellPositionQuantityDtoList;
}
