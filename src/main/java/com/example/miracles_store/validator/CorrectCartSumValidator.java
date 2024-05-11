package com.example.miracles_store.validator;

import com.example.miracles_store.dto.OrderRequestDto;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.validator.annotation.CorrectCartSum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CorrectCartSumValidator implements ConstraintValidator<CorrectCartSum, OrderRequestDto> {

    private final SellPositionService sellPositionService;

    @Override
    public boolean isValid(OrderRequestDto orderRequestDto, ConstraintValidatorContext context) {
        BigDecimal orderSum = orderRequestDto.getSellPositionQuantitySet().stream().map(sellPositionQuantity -> {
            SellPosition sellPosition = sellPositionService.getById(sellPositionQuantity.getSellPositionId());
            return sellPosition.getProduct().getCost().multiply(BigDecimal.valueOf(sellPositionQuantity.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return orderSum.equals(orderRequestDto.getSum());
    }
}
