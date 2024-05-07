package com.example.miracles_store.validator;

import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.validator.annotation.ProductIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductIdExistsValidator implements ConstraintValidator<ProductIdExists, Integer> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext context) {
        if (Objects.isNull(productId)) {
            return false;
        }
        return productRepository.existsById(productId);
    }
}
