package io.onedev.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import io.onedev.server.validation.validator.PatternsValidator;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PatternsValidator.class) 
public @interface Patterns {

	String message() default "";
	
	String suggester() default "";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    boolean path() default false;
}
