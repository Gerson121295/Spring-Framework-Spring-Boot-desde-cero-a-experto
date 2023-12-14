package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.PaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	
	@Autowired
	private PaisService service; //Inyecta la interfaz PaisService
	
	@Override
	public void setAsText(String idString) throws IllegalArgumentException { //(String idString) recibe el id del pais, 
		if(idString != null && idString.length() > 0 ) {//Evalua si el dato escrito en el form no es nulo y sea mayor a 0 (el campo no este vacio o espacios en blancos)
		
			try {
			Integer id = Integer.parseInt(idString); //Hace una conversion del dato recibido a tipo Integer
			this.setValue(service.obtenerPorId(id)); //evalua el dato recibido con el id si es 
			} catch(NumberFormatException e) {
				setValue(null); //Si falla el valor sea null
			}
		} else {
			setValue(null); //Si falla el valor sea null
		}
	
	}
}


