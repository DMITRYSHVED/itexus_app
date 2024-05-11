package com.example.miracles_store.validator;

import com.example.miracles_store.validator.annotation.PhoneFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneFormatValidator implements ConstraintValidator<PhoneFormat, String> {

    private static final String PHONE_NUMBER_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phone.matches(PHONE_NUMBER_REGEX);
    }
}
