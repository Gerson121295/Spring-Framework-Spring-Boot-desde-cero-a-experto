package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="facturas_items")
public class ItemFactura implements Serializable {
	
	private static final long serialVersionUID = 1L; //Como se implementa Serializable
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;
	
	//Relacion de la clases ItemFactura con Producto: @ManyToOne Muchos(Many) tienen o para(to) un(One) Producto
	//Solo mapearemos Unidireccional de un via de ItemFactura a Producto
	//al mapear producto en ItemFactura se va a crear el campo productoId en la tabla facturas_items de forma automatica o es opcional definirlo con el @JoinColumn
	//@JoinColumn(name="producto_id") con el JoinColumn se define la llave foranea que relaciona ambas tablas ItemFactura y Producto, 
	//el campo que se crea automaticamente es producto_id en la tabla ItemFactura
	@ManyToOne(fetch=FetchType.LAZY) //EAGER: trae los productos rapidamente, se agrega cuando se tiene que mostrar datos de otras tablas en esta caso se necesita mostrar los productos del cliente
	@JoinColumn(name="producto_id") //Opcional agregarlo al no agregarlo se crea por defecto el atributo producto_id en la tabla ItemFactura como llave foranea para unir o relacionar ambas tablas ItemFactura y Producto 
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) //Se agrego @JsonIgnoreProperties para  Ignorar algunos atributos o propiedades en el JSON, esta anotacion sustituye fetch=FetchType.LAZY agregar a la relacion como //EAGER: trae los productos rapidamente.
	private Producto producto;  
	
	//En la clase que tenga el @Many(Muchos) creo que se crea el id (producto_id) por defecto, en este caso se crea en la clase ItemFactura
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	//Metodo para calcular el importe
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}
	
	
}




