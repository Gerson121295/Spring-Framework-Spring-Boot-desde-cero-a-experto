package com.bolsadeideas.springboot.webfluxapp.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bolsadeideas.springboot.webfluxapp.models.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{ //La clase Producto, El tipo de dato de su Id
	

}
