package com.example.miracles_store.dto;

import com.example.miracles_store.entity.enums.OrderStatus;
import com.example.miracles_store.entity.order.SellPositionQuantityResponse;
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

    private OrderStatus orderStatus;

    private BigDecimal sum;

    private AddressResponseDto address;

    private UserResponseDto user;

    private List<SellPositionQuantityResponse> orderSellPositions;
}
