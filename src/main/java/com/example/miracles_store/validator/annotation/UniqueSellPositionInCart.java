package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.UniqueSellPositionInCartValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {UniqueSellPositionInCartValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueSellPositionInCart {
    String message() default "This sellPosition is already in cart";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
