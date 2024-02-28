package com.gerson.springboot.app.springbootcrudjpa.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Otro ejemplo de validar los atributos de la clase Product al Crear o actualizar un producto
@Constraint(validatedBy = RequiredValidation.class) //une la clase RequiredValidation con esta interfaz IsRequired
@Retention(RetentionPolicy.RUNTIME) //Se ejecuta en tiempo de ejecucion
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface IsRequired {

    String message() default " es requerido usando anotaciones.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
