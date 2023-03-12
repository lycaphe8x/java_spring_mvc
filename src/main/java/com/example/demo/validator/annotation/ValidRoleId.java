package com.example.demo.validator.annotation;

import com.example.demo.validator.ValidRoleIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidRoleIdValidator.class)
@Documented
public @interface ValidRoleId {

        String message() default "Invalid Role ID";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
