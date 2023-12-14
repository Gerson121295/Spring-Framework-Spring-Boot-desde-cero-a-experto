package com.bolsadeideas.springboot.form.app.models.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Pais {

	//@NotNull
	private Integer id;
	@NotEmpty
	private String codigo;
	private String nombre;

	// Constructor con parametros
	public Pais(Integer id, String codigo, String nombre) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	// Constructor sin parametros
	public Pais() {
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Para que por defecto aparezca seleccionado un pais en la lista
	@Override
	public String toString() {
		
		return this.id.toString(); //id es int y  con .toString()  lo convertimos en un String
	}

}




