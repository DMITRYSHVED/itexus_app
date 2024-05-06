package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.CorrectCartSumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {CorrectCartSumValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectCartSum {
    String message() default "Invalid order sum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
