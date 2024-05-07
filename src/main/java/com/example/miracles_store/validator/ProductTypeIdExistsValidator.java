package com.example.miracles_store.validator;

import com.example.miracles_store.repository.ProductTypeRepository;
import com.example.miracles_store.validator.annotation.ProductTypeIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductTypeIdExistsValidator implements ConstraintValidator<ProductTypeIdExists, Integer> {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public boolean isValid(Integer productTypeId, ConstraintValidatorContext context) {
        if (Objects.isNull(productTypeId)) {
            return false;
        }
        return productTypeRepository.existsById(productTypeId);
    }
}
