package com.example.miracles_store.validator;

import com.example.miracles_store.repository.SellPositionRepository;
import com.example.miracles_store.validator.annotation.SellPositionIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SellPositionIdExistsValidator implements
        ConstraintValidator<SellPositionIdExists, Integer> {

    private final SellPositionRepository sellPositionRepository;

    @Override
    public boolean isValid(Integer sellPositionId, ConstraintValidatorContext context) {
        if (Objects.isNull(sellPositionId)) {
            return false;
        }
        return sellPositionRepository.existsById(sellPositionId);
    }
}
