package org.example.identityservice.validator;
// Declare

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) // where to be applied
@Retention(RetentionPolicy.RUNTIME) // when to be applied
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    int min() default 18;

    String message() default "Invalid Date of Birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
