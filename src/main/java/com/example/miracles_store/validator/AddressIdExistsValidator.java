package com.example.miracles_store.validator;

import com.example.miracles_store.repository.AddressRepository;
import com.example.miracles_store.validator.annotation.AddressIdExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AddressIdExistsValidator implements ConstraintValidator<AddressIdExists, Integer> {

    private final AddressRepository addressRepository;

    @Override
    public boolean isValid(Integer addressId, ConstraintValidatorContext context) {
        if (Objects.isNull(addressId)) {
            return false;
        }
        return addressRepository.existsById(addressId);
    }
}
