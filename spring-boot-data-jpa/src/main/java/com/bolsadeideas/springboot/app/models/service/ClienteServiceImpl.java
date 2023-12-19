package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;


@Service
public class ClienteServiceImpl implements IClienteService {

	
	@Autowired
	private IClienteDao clienteDao; //Se inyecta IClienteDao
	
	
	//Como es una fachada accedemos a los metodos de IClienteDao
	
	/*Es una fachada aqui tenemos un solo DAO(IClienteDao) pero podemos tener mas DAOS y del Service podemos
	acceder a diferentes DAOS, como un punto de acceso. Otra cosa es que en el Service podemos agregar las anotaciones
	@transaccional que se encuentran en la clase que implementa el Dao  ClienteDaoImpl
	*/
	
	@Override
	@Transactional(readOnly=true) //Se agrega true debido a que es un Select si fuera un insert se elimina readOnly=true
		public List<Cliente> findAll() {	
		return (List<Cliente>) clienteDao.findAll();  //utilizando forma 1 DAO:  clienteDao.findAll();
	}

	@Override
	@Transactional //Insertar un nuevo registro
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {	
		return clienteDao.findById(id).orElse(null);  //si encuentra retorna el objeto sino retorna null //utilizando forma 1 DAO: return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);  // //utilizando forma 1 DAO:  clienteDao.delete(id);
	}

	//Listar metodos - Paginacion
	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}


}



