package com.example.miracles_store.validator;

import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.validator.annotation.CartItemsAreActive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CartItemsAreActiveValidator implements ConstraintValidator<CartItemsAreActive, Set<SellPositionQuantity>> {

    private final SellPositionService sellPositionService;

    @Override
    public boolean isValid(Set<SellPositionQuantity> sellPositionQuantitySet, ConstraintValidatorContext context) {
        return sellPositionQuantitySet.stream().noneMatch(sellPositionQuantity -> sellPositionService.
                getById(sellPositionQuantity.getSellPositionId()).getIsActive().equals(false));
    }
}
