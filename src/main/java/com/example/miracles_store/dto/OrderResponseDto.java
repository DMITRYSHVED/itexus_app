package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Integer id;

    private String phone;

    private String orderComment;

    private String orderStatus;

    private BigDecimal sum;

    private AddressResponseDto address;

    private UserResponseDto user;

    private List<SellPositionQuantityDto> orderSellPositions;
}
