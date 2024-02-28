package com.gerson.springboot.app.springbootcrudjpa.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidation implements ConstraintValidator<IsRequired, String>{ //IsRequired clase de validacion creada, String es el tipo de dato a validar

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //Valida que no sea nulo, vacio y que no este en blanco.
 /*      if(value != null  && !value.isEmpty()  && !value.isBlank()){
        return true;
       }
       return false;
    }
*/ 

//Forma 2 mas optimizada para no usar el if
    //return (value != null  && !value.isEmpty()  && !value.isBlank());

    //Forma 3 de validar un String
    return StringUtils.hasText(value); //Si tiene texto es true sino es false
}
}
