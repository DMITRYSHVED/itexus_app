package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.CartItemsAreActiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {CartItemsAreActiveValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CartItemsAreActive {
    String message() default "You can't order not active selPositions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
