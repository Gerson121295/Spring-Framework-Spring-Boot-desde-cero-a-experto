package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequeridoValidador implements ConstraintValidator<Requerido, String> {//Anotacion Requerido, y el tipo del campo a evaluar String

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//if(value == null || value.isEmpty() || value.isBlank()) {  //Forma 1 de validar
		if(value == null || !StringUtils.hasText(value)) {  //Forma 2 de validar.  !StringUtils.hasText(value)= si no tiene contenido. StringUtils.hasText(value)  valida true porque tiene contenido
			return false;
		}
		return true;
	}

}


