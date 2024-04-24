package com.example.miracles_store.validator;

import com.example.miracles_store.dto.SellPositionRequestDto;
import com.example.miracles_store.repository.SellPositionRepository;
import com.example.miracles_store.validator.annotation.UniqueSellPosition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UniqueSellPositionValidator implements ConstraintValidator<UniqueSellPosition, SellPositionRequestDto> {

    private final SellPositionRepository sellPositionRepository;

    @Override
    public boolean isValid(SellPositionRequestDto sellPosition, ConstraintValidatorContext context) {
        if (Objects.nonNull(sellPosition.getId())) {
            return !sellPositionRepository.existsByProductAndSizeAndIdNotEquals(sellPosition.getProductId(),
                    sellPosition.getSize(), sellPosition.getId());
        } else {
            return !sellPositionRepository.existsByProductAndSize(sellPosition.getProductId(), sellPosition.getSize());
        }
    }
}
