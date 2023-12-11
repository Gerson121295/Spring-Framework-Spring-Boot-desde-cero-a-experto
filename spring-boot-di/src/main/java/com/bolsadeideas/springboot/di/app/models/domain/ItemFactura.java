package com.bolsadeideas.springboot.di.app.models.domain;

public class ItemFactura {

	private Producto producto;
	private Integer cantidad;
	
	//Constructores
	public ItemFactura(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	

	// Getters and Setters

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public Integer calcularImporte() {
		return cantidad * producto.getPrecio();
	}

}
