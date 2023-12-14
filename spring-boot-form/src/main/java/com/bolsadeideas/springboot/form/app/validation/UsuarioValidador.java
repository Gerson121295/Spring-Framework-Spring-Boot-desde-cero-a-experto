package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;


@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Se desabilita debido a que se usará la validacion: de la clase IdentificadorRegex en el campo identificador2 de la clase de usuario
	
		//	Usuario usuario = (Usuario)target; //Casteo del target pasa a ser Usuario
		
		//Forma de validar
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.usuario.nombre");//Valida error, en el campo "nombre", y define el mensaje a mostrar(definido en messages.properties.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "requerido.usuario.nombre");//Valida error, en el campo "nombre", y define el mensaje a mostrar(definido en messages.properties.

		//if(usuario.getIdentificador2().matches("[0-9]{2}[,.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}") == false) {
/*		if(!usuario.getIdentificador2().matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) { //IF de Forma optimizada
			errors.rejectValue("identificador2", "Pattern.usuario.identificador2");
		}
*/		
	}
	
	

}
