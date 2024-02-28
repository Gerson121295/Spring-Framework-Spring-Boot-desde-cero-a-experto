package com.gerson.springboot.app.springbootcrudjpa.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gerson.springboot.app.springbootcrudjpa.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//Validacion personalizada usando anotacion check DB
//Esta clase IsExistsDbValidation  conecta con la interfaz IsExistsDb 

@Component
public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String>{ //la interfaz creada para validar y el tipo de dato a validar tipo String

    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //Si es distinto a null  y si es distinto al sku en la BD de algun producto
       // return value != null && !service.existsBySku(value);
       return !service.existsBySku(value); //Solo valida por la BD
    } 

}
