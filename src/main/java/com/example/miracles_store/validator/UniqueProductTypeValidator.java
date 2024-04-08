package com.example.miracles_store.validator;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.repository.ProductTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniqueProductTypeValidator implements ConstraintValidator<UniqueProductType, ProductTypeDto> {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public boolean isValid(ProductTypeDto productType, ConstraintValidatorContext context) {
        if (productTypeRepository.existsByName(productType.getName())) {
            if (productType.getId() != null) {
                return productTypeRepository.findByName(productType.getName()).get().getId() == productType.getId();
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
