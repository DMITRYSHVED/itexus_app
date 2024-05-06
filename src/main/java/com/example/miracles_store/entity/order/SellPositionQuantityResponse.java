package com.example.miracles_store.entity.order;

import com.example.miracles_store.dto.SellPositionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellPositionQuantityResponse {

    private SellPositionResponseDto sellPositionResponseDto;

    private Integer quantity;
}
