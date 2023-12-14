package com.bolsadeideas.springboot.form.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

@Constraint(validatedBy = RequeridoValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Requerido {

	//Ir a la clase usuario dar clic a la anotacion @NotBlank y copiar los 3 metodos siguientes
	
	String message() default "El Campo es requerido - Usando anotaciones";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}





