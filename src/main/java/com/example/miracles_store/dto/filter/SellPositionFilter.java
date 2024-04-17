package com.example.miracles_store.dto.filter;

public record SellPositionFilter(Integer productId, String positionSize, Integer quantity, Boolean isActive) {
}
