package com.example.miracles_store.validator;

import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueProductValidator implements ConstraintValidator<UniqueProduct, ProductRequestDto> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(ProductRequestDto product, ConstraintValidatorContext context) {
        if (productRepository.existsByName(product.getName())) {
            if (product.getId() != null) {
                return productRepository.findByName(product.getName()).get().getId() == product.getId();
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
