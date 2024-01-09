package com.bolsadeideas.springboot.app.view.xml;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

//Los datos que se convertira a XML 

@XmlRootElement(name="clientesList")
public class ClienteList {
	
	@XmlElement(name="cliente") //indica que elementos sera elementos xml, cliente(tebdra los datos del cliente por eso en singular xq habrán muchos)
	public List<Cliente> clientes;

	public ClienteList() {}

	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	//Getters 
	public List<Cliente> getClientes() {
		return clientes;
	}

	
}
