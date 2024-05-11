package com.example.miracles_store.dto.filter;

import java.math.BigDecimal;

public record OrderFilter (BigDecimal sum, String orderStatus, Integer addressId, Integer userId) {}
