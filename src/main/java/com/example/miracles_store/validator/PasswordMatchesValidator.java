package com.example.miracles_store.validator;

import com.example.miracles_store.dto.auth.SignUpRequest;
import com.example.miracles_store.validator.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {

    @Override
    public boolean isValid(SignUpRequest request, ConstraintValidatorContext context) {
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
