package com.gerson.springboot.app.springbootcrudjpa.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Validacion personalizada usando anotacion check DB 
//Esta interfaz IsExistsDb conecta con la clase IsExistsDbValidation

@Constraint(validatedBy = IsExistsDbValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsExistsDb {

	String message() default "Ya existe en la BD";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
