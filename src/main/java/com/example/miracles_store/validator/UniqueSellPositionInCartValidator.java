package com.example.miracles_store.validator;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.OrderCartRepository;
import com.example.miracles_store.validator.annotation.UniqueSellPositionInCart;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UniqueSellPositionInCartValidator implements ConstraintValidator<UniqueSellPositionInCart, OrderCartRequestDto> {

    private final OrderCartRepository orderCartRepository;

    @Override
    public boolean isValid(OrderCartRequestDto orderCartRequestDto, ConstraintValidatorContext context) {
        if (!orderCartRepository.existsById(orderCartRequestDto.getUserId())) {
            return true;
        }
        OrderCart storedCart = orderCartRepository.findById(orderCartRequestDto.getUserId()).
                orElseThrow(() -> new ObjectNotFoundException("Can't find order Cart by user with id: " +
                        orderCartRequestDto.getUserId()));
        Set<SellPositionQuantity> storedSellPositionQuantitySet = storedCart.getSellPositionQuantitySet();

        return storedSellPositionQuantitySet.stream().noneMatch(sellPositionQuantity -> sellPositionQuantity.
                getSellPositionId().equals(orderCartRequestDto.getSellPositionId()));
    }
}
