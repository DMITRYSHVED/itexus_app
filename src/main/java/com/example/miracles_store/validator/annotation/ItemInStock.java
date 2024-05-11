package com.example.miracles_store.validator.annotation;

import com.example.miracles_store.validator.ItemInStockValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {ItemInStockValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemInStock {
    String message() default "We don't have that many items in stock";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
