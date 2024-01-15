package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

@Service  
public class ClienteServiceImpl implements IClienteService{
	
	@Autowired
	private IClienteDao clienteDao; //IClienteDao Contiene los metodos del CRUD

	//Mostrar todos los clientes
	@Override
	@Transactional(readOnly = true) //Solo porque es de lectura se agrega readOnly= True, si fuera create, update o delete no se agregaria readOnly= True solo quedaria @Transactional.
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	//Buscar por id a un cliente
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null); //Si lo encuentra retorna el objeto cliente sino retorna un null
	}

	//Guardar cliente
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	//Eliminar un cliente
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);	
	}

}
