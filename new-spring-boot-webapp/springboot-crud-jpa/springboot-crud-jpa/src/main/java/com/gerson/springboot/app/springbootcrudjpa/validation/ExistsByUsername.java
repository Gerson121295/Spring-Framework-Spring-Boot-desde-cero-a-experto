package com.gerson.springboot.app.springbootcrudjpa.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistsByUsernameValidation.class) //Clase ExistsByUsernameValidation que imlementa la interfaz
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {

        String message() default "Ya existe en la BD!, escoja otro username";

        Class<?>[] groups() default { };
    
        Class<? extends Payload>[] payload() default {};
}
