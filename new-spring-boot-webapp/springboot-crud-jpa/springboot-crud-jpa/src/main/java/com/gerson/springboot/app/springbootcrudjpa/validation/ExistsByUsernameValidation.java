package com.gerson.springboot.app.springbootcrudjpa.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gerson.springboot.app.springbootcrudjpa.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String>{ //recibe la anotacion:ExistsByUsername, y el tipo de dato a validar String

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        
        if(service == null) {  //Si el service es igual a null o es valido retornamos true;
            return true;
        }

        return !service.existsByUsername(username); //el username no sea igual al de la BD. Es valido si ya es distinto.
    } 

}
