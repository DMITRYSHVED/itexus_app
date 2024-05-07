package com.example.miracles_store.validator;

import com.example.miracles_store.repository.UserRepository;
import com.example.miracles_store.validator.annotation.UserIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserIdExistsValidator implements ConstraintValidator<UserIdExists, Integer> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        if (Objects.isNull(userId)) {
            return false;
        }
        return userRepository.existsById(userId);
    }
}
