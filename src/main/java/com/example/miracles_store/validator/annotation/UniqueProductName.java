package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.UniqueProductNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {UniqueProductNameValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {
    String message() default "Product with this name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
