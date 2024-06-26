package com.example.miracles_store.validator;

import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.validator.annotation.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, ProductRequestDto> {
    private final ProductRepository productRepository;

    @Override
    public boolean isValid(ProductRequestDto product, ConstraintValidatorContext context) {
        if (Objects.nonNull(product.getId())) {
            return !productRepository.existsByNameAndIdNotEqual(product.getName(), product.getId());
        } else {
            return !productRepository.existsByName(product.getName());
        }
    }
}
