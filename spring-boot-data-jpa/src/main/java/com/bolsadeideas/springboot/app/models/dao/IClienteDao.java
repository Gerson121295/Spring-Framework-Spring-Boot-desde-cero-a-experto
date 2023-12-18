package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

	
	//Creacion de DAO usando CrudRepository Forma 2: Al extender de CrudRepository ya trae metodos (Precionar Ctrl + clic en CrudRepository para ver los metodos)

public interface IClienteDao extends CrudRepository<Cliente, Long>{ //Se agrega la clase a usar "Cliente", y el tipo de clave primaria "Long" 
	
	
	
}


