package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity //Indicar que es una clase de persistencia y esta mapeado a la BD
@Table(name="authorities", uniqueConstraints = {@UniqueConstraint(columnNames= {"user_id","authority"})})//Nombre de la clase en la tabla de la BD
// con @UniqueConstraint(columnNames= {"user_id","authority"}) especificamos que esas columnas en la tabla serán unicas
public class Role implements Serializable{ //Serializabe por si en algun momento queremos guardar en la sesion http y pueda ser persistente entre request

	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String authority; //nombre del rol
	
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
}



