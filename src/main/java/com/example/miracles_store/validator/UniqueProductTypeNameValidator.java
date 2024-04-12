package com.example.miracles_store.validator;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.repository.ProductTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UniqueProductTypeNameValidator implements ConstraintValidator<UniqueProductTypeName, ProductTypeDto> {
    private final ProductTypeRepository productTypeRepository;

    @Override
    public boolean isValid(ProductTypeDto productType, ConstraintValidatorContext context) {
        if (Objects.nonNull(productType.getId())) {
            return !productTypeRepository.existsByNameAndIdNotEqual(productType.getName(), productType.getId());
        } else {
            return !productTypeRepository.existsByName(productType.getName());
        }
    }
}
