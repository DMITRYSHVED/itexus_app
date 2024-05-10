package com.example.miracles_store.validator;

import com.example.miracles_store.validator.annotation.PhoneFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneFormatValidator implements ConstraintValidator<PhoneFormat, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        String phoneRegex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        return phone != null && phone.matches(phoneRegex);
    }
}
