package com.example.miracles_store.validator;

import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.validator.annotation.ProductIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductIdExistsValidator implements ConstraintValidator<ProductIdExists, Integer> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext context) {
        return productId != null && productRepository.existsById(productId);
    }
}
