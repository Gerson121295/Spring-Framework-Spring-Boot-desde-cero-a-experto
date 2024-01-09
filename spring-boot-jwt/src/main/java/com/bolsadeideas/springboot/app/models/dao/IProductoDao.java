package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.app.models.entity.Producto;

//Al extender de JpaRepository ya trae metodos de un CRUD el cual se le envian la clase(Producto) y el tipo de dato(Long) del id de la clase.
public interface IProductoDao extends JpaRepository<Producto, Long>{
	
	//Forma 1 de hacer la consulta JPA para buscar productos 
	@Query("select p from Producto p where p.nombre like %?1%") //%?1% cunado parametro sea igual a term(termino). //Select a nivel de entity no a travez de tabla
	public List<Producto> findByNombre(String term); //Encontrar el producto por nombre
	
	//Forma 2 de hacer la consulta JPA para buscar productos por nombre
	public List<Producto> findByNombreLikeIgnoreCase(String term);
	
}


