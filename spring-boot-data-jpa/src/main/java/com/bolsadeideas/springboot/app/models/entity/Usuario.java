package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity //Indicar que es una clase de persistencia y esta mapeado a la BD
@Table(name="usuarios") //Nombre de la clase en la tabla de la BD
public class Usuario implements Serializable{ //Serializabe por si en algun momento queremos guardar en la sesion http y pueda ser persistente entre request

	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length=40)  //El campo username debe ser unico y debe tener un largo de 40 caracteres
	private String username;
	
	@Column(length=60) //pasword tiene un largo de 60 caracteres
	private String password;
	
	private Boolean enabled;
	
	
	//Relacion con clase Role: Un usuario puede tener muchos roles
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id") //Del OneToMany La clase que tenga el Many(Role) se creará automaticamente el id(user_id) de la llave foranea que relaciona ambas clases
	private List<Role> roles; //En la clase Role que es la tabla authorities se creará el atributo user_id

	
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	

}



