package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

//public interface IClienteDao extends JpaRepository<Cliente, Long>{ //Recibe como parametros: Cliente(la Clase) y Long(El tipo de su ID) 
public interface IClienteDao extends CrudRepository<Cliente, Long> { // La interface CrudRepository ya tiene metodos para un CRUD por lo que podemos acceder directamente
	
}
