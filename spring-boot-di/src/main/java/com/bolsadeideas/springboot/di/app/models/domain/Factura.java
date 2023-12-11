package com.bolsadeideas.springboot.di.app.models.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@RequestScope //al agregar la anotacion: @RequestScope el bean va a dura lo que dura la peticion del usuario. Cada usuario tendra una factura diferente.
//@SessionScope //Marcamos la clase como una sesion y se debe implementar la interfaz Serializable
//@ApplicationScope 
public class Factura implements Serializable{  


	private static final long serialVersionUID = 946004357128146951L; //Al implementar Serializable debemos implementar serialVersionUID
	

	@Value("${factura.descripcion}")
	private String descripcion;

	@Autowired //Injectar el cliente
	private Cliente cliente;
	
	@Autowired
	//@Qualifier("itemsFacturaOficina") //Injectar el bean o tambien se puede especificar Primary al bean
	private List<ItemFactura> items;
	
	
	
	//Ciclo de vida del componente
		//Creacion del objeto
	@PostConstruct
	public void inicializar() {
		cliente.setNombre(cliente.getNombre().concat(" ").concat("Marin"));
		descripcion = descripcion.concat(" del cliente: ".concat(cliente.getNombre()));
	}
	
		//Destruccion del objeto
	@PreDestroy
	public void destruir() {
		System.out.println("Factura destruida: ".concat(descripcion)); //Se verá en la terminal al parar el servidor.
	}
	
	

	// Getters and Setters

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

}
