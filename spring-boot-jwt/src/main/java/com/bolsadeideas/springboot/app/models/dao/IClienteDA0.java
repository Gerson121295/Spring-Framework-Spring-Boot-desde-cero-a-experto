package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteDA0 {
	
	//Creacion de DAO Forma 1

	public List<Cliente> findAll(); //Metodo listar todos los clientes

	public void save(Cliente cliente); //Guardar un cliente
	
	public Cliente findOne(Long id);//Metodo buscar por id
	
	public void delete(Long id);
}
