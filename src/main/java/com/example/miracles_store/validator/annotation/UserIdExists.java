package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.UserIdExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {UserIdExistsValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdExists {
    String message() default "User with this ID does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
