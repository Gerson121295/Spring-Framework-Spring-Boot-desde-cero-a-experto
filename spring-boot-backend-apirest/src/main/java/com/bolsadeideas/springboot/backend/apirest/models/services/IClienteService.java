package com.bolsadeideas.springboot.backend.apirest.models.services;
import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
public interface IClienteService {
	
	//Firma de los metodos CRUD para Cliente
	
	//Mostrar todos los clientes
	public List<Cliente> findAll(); 
	
	//Buscar por id a un cliente
	public Cliente findById(Long id);
	
	//Guardar cliente
	public Cliente save(Cliente cliente);
	
	//Eliminar un cliente
	public void delete(Long id);
	

}
