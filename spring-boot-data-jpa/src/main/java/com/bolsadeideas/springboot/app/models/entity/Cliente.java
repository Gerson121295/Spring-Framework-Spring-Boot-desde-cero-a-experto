package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(name="nombre_cliente") //@Column permite especificar como esta
	// nombrado el atributo en la tabla de la BD.
	@NotEmpty //Validacion campo requerido no puede ir vacio. NotEmpty solo se usa con String
	@Size(min=3, max=12) //Tamaño del nombre debe estar entre 4 y 12 caracteres
	private String nombre;
	
	@NotEmpty
	private String apellido;// Cuando en la tabla de la BD es igual al nombre del atributo en la clase no es
							// necesario agregarle @Column
	@NotEmpty
	@Email  //formato email debe ser validado
	private String email;

	@NotNull //valida que la fecha no sea nula. @NotNull se usa con decimal, int, integer, long y Date
	@Column(name = "create_at") // Como se llama este campo en la tabla de la BD
	@Temporal(TemporalType.DATE) // Indica el formato en que se va a guardar la fecha en la tabla de la BD
	@DateTimeFormat(pattern="yyyy-MM-dd") //Establece el formato a recibir la fecha
	private Date createAt;
	
	private String foto;
	
/*
	//Metodo antes de que se guarde en la BD - Crea la Fecha actual automaticamente
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
*/
	
	// Getters and Setters
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

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
