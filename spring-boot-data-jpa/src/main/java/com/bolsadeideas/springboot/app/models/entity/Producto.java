package com.bolsadeideas.springboot.app.models.entity;

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

@Entity
@Table(name="productos")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) //Se agrego @JsonIgnoreProperties para  Ignorar algunos atributos o propiedades en el JSON, esta anotacion sustituye fetch=FetchType.LAZY en ItemFactura agregar a la relacion como //EAGER: trae los productos rapidamente.
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L; //Como se implementa Serializable
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Double precio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	//Metodo - Crea la Fecha actual automaticamente al crear el registro
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	
	//Constructor
	public Producto() {
	}
	
	
	//Getters and Setters
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
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
}
