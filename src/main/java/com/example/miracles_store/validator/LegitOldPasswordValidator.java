package com.example.miracles_store.validator;

import com.example.miracles_store.dto.PasswordChangeDto;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.repository.UserRepository;
import com.example.miracles_store.validator.annotation.LegitOldPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegitOldPasswordValidator implements ConstraintValidator<LegitOldPassword, PasswordChangeDto> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean isValid(PasswordChangeDto passwordChangeDto, ConstraintValidatorContext context) {
        User user = userRepository.findById(passwordChangeDto.getUserId()).orElse(null);

        if (user != null) {
            return passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword());
        }
        return false;
    }
}
