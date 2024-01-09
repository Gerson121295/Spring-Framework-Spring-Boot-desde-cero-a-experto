package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.app.models.entity.Usuario;


public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	//Metodo de busqueda personalizada
	public Usuario findByUsername(String username); //findByUsername=buscar un usuario por el campo username(Username)

	//Otra forma usando QUERY:
//A traves del nombre del metodo(Query method name), se ejecutará la consulta JPQL: "select u from Usuario u where u.username=?1"

	@Query("select u from Usuario u where u.username=?1")
	public Usuario fetchByUsername(String username);
	
}

