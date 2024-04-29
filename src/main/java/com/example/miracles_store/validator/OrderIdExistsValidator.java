package com.example.miracles_store.validator;

import com.example.miracles_store.repository.OrderRepository;
import com.example.miracles_store.validator.annotation.OrderIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderIdExistsValidator implements ConstraintValidator<OrderIdExists, Integer> {

    private final OrderRepository orderRepository;

    @Override
    public boolean isValid(Integer orderId, ConstraintValidatorContext context) {
        return orderRepository.existsById(orderId);
    }
}
