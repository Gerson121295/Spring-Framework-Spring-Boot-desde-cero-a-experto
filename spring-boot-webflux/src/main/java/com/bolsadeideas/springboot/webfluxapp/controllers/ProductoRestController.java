package com.bolsadeideas.springboot.webfluxapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.webfluxapp.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webfluxapp.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
	
	@Autowired
	private ProductoDao dao;
	
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@GetMapping() // Prueba: http://localhost:8080/api/productos
	public Flux<Producto> index(){
		
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		})
				.doOnNext(prod -> log.info(prod.getNombre()));
		
		return productos;
	}
	
	//Obtener por id
	@GetMapping("/{id}")  //Prueba:  http://localhost:8080/api/productos/65aee0ed56398e69feb95e23
	public Mono<Producto> show(@PathVariable String id){
		//Forma 1
		//Mono<Producto> producto = dao.findById(id); 
		//return producto;
		
		//Forma2:
		Flux<Producto> productos = dao.findAll();
		
		Mono<Producto> producto = productos.filter(p-> p.getId().equals(id)).next()
				.doOnNext(prod -> log.info(prod.getNombre())); // Mostrar el valor del objeto en consola
		
		return producto;
	}

}
