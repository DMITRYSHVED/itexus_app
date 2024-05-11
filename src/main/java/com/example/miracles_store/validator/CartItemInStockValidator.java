package com.example.miracles_store.validator;

import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.validator.annotation.CartItemsInStock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CartItemInStockValidator implements ConstraintValidator<CartItemsInStock, Set<SellPositionQuantity>> {

    private final SellPositionService sellPositionService;

    @Override
    public boolean isValid(Set<SellPositionQuantity> sellPositionQuantitySet, ConstraintValidatorContext context) {
        return sellPositionQuantitySet != null && sellPositionQuantitySet.stream().noneMatch(sellPositionQuantity -> sellPositionService.
                getById(sellPositionQuantity.getSellPositionId()).getQuantity() < sellPositionQuantity.getQuantity());
    }
}
