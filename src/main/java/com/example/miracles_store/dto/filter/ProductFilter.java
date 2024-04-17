package com.example.miracles_store.dto.filter;

import java.math.BigDecimal;

public record ProductFilter(String name, BigDecimal cost, Integer productTypeId) {
}
