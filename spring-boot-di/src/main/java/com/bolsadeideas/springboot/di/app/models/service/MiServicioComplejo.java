package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component("miServicioComplejo")  //Agregar un nombre al componente para identificarlo es opcional. // Puede tener la anotacio @Component o @Service
public class MiServicioComplejo implements IServicio{ //Implementa la interfaz IServicio

	public String operacion() {
		return "Ejecutando algun proceso complicado";
	}
}

