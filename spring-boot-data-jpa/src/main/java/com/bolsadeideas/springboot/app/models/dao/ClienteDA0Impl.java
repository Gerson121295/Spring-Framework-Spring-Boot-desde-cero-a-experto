package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository //("clienteDaoJPA")
public class ClienteDA0Impl implements IClienteDA0{
	
	//Implementacion de DAO Forma 1.  La forma 2 No requiere una clase que implemente el DAO, lo inyecta al ClienteServiceImpl 
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	//@Transactional(readOnly=true) //Se agrega true debido a que es un Select si fuera un insert se elimina readOnly=true
	@Override
	public List<Cliente> findAll() {

		return em.createQuery("from Cliente").getResultList();
	}


	@Override
	//@Transactional(readOnly=true) //se implementa en la clase ClienteServiceImpl
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}
	
	
	@Override
	//@Transactional //Insertar un nuevo registro  //se implementa en la clase ClienteServiceImpl
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() >0) { //Si se cumple entonces vamos a editar
			em.merge(cliente);
		}else { //si no hacemos un persist crearemos un nuevo usuario
			em.persist(cliente);
		}	
	}


	@Override
	//@Transactional  //se implementa en la clase ClienteServiceImpl
	public void delete(Long id) {
		Cliente cliente = findOne(id); //Obtenemos el cliente a eliminar por su id
		em.remove(cliente); //Pasamos el cliente a eliminar
		//em.remove(findOne(id)); //otra forma de hacerlo en 1 sola linea: se remueve y se pasa el id del cliente eliminar al metodo findOne
	}


}
