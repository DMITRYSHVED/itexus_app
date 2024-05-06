package com.example.miracles_store.dto;

import com.example.miracles_store.entity.order.SellPositionQuantityResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartResponseDto {

    private UserResponseDto user;

    private Set<SellPositionQuantityResponse> sellPositionQuantityDtoSet;
}
