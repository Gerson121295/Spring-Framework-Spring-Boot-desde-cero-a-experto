package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity //persistencia: define el componente y especifica que una clase es una entidad
@Table(name="clientes") //cuando la tabla se llama igual que la clase no es necesario agregar name, solo se deja la anotacion: @Table
public class Cliente implements Serializable{ //Serializable para guardar atributos en sesion ya que se trabaja con formulario
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //La clave primaria se agregar e incrementa automaticamente
	private Long id;
	
	//@Column(name="nombre", nullable=false)  -Solo se agrega la anotacion @Column cuando el atributo de la clase se llame diferente con el nombre del campo de la tabla de la BD
	@NotEmpty(message="no puede estar vacio") //No puede ir vacio el campo
	@Size(min=4, max=12, message="El tamaño debe estar entre 4 y 12 caracteres") //El minimo de caracteres es de 4 y el maximo es de 12 para el nombre
	@Column(nullable = false) //el campo de la tabla de la base de datos no puede ser nulo
	private String nombre;
	
	@NotEmpty(message="no puede estar vacio") //@NotEmpty No puede ir vacio el campo
	private String apellido;
	
	@Email(message="no es una direccion de correo bien formada") //El campo correo debe tener un formato de correo
	@NotEmpty(message="no puede estar vacio") //No puede ir vacio el campo
	@Column(nullable = false, unique=true) //El email no puede ir vacio o falso y debe ser unico
	private String email;
	
	@Column(name="create_at") //Se agrego column porque el atributo createAt de la clase "Cliente", en la tabla clientes de la BD se llamara create_at
	@Temporal(TemporalType.DATE) //A través de esta anotación le asignamos a este campo una conexión entre el formato Date de Java y el formato de Fecha que maneja la base de datos.
	private Date createAt;
	
	//para agregar la fecha de creacion cuando se crea el cliente
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	
	//Metodos Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	

}
