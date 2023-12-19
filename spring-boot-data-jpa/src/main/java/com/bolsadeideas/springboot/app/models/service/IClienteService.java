package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteService {
	
	//Metodos de IClienteDao
	public List<Cliente> findAll(); //Metodo listar todos los clientes.

	public Page<Cliente> findAll(Pageable pageable); //Paginación -Metodo listar todos los clientes.
	
	public void save(Cliente cliente); //Guardar un cliente.
	
	public Cliente findOne(Long id);//Metodo buscar por id.
	
	public void delete(Long id);

}
