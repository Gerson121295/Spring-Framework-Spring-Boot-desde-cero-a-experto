package com.bolsadeideas.springboot.di.app.models.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@RequestScope //al agregar esta anotacion el bean va a dura lo que dura la peticion del usuario. Cada usuario tendra una factura diferente.
public class Cliente {

	
	@Value("${cliente.nombre}") //Se inyecta el valor del nombre desde archivo properties
	private String nombre;
	
	@Value("${cliente.apellido}")
	private String apellido;

	// Getters and Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
