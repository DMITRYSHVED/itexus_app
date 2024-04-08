package com.example.miracles_store.validator;

import com.example.miracles_store.repository.ProductTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductTypeIdExistsValidator implements ConstraintValidator<ProductTypeIdExists, Integer> {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public boolean isValid(Integer productTypeId, ConstraintValidatorContext context) {
        return productTypeRepository.existsById(productTypeId);
    }
}
