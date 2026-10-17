package com.example.spacecatsmarket.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CosmicWordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {

	String message() default "must contain a cosmic word: star, galaxy, comet, nebula, cosmic, lunar, orbit, asteroid, meteor";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
