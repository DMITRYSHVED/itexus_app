package com.example.miracles_store.validator;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.SellPositionRepository;
import com.example.miracles_store.validator.annotation.ItemInStock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemInStockValidator implements ConstraintValidator<ItemInStock, OrderCartRequestDto> {

    private final SellPositionRepository sellPositionRepository;


    @Override
    public boolean isValid(OrderCartRequestDto orderCart, ConstraintValidatorContext context) {
        SellPosition sellPosition = sellPositionRepository.findById(orderCart.getSellPositionId()).orElseThrow(() ->
                new ObjectNotFoundException("Can't find sellPosition with id: " + orderCart.getSellPositionId()));
        return orderCart.getQuantity() <= sellPosition.getQuantity();
    }
}
