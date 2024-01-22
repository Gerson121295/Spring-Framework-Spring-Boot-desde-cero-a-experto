package com.bolsadeideas.springboot.webfluxapp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webfluxapp.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webfluxapp.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{ //Se implementa la interfaz CommandLineRunner para ejecutar por consola

	@Autowired
	private ProductoDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("productos").subscribe(); //Elimina la coleccion de productos para que en la BD no se siga agregando mas. al ejecutar se elimina los que estan luego se crea otros productos.
		
		Flux.just(
				new Producto("TV Panasonic Pantalla LCD", 456.89),
				new Producto("Sony Camara hd digital", 177.89),
				new Producto("Apple Ipod", 56.89),
				new Producto("Sony NoteBook", 156.89),
				new Producto("Hewlet package", 550.89),
				new Producto("Bianchi bicicleta", 457.20),
				new Producto("HP Notebook", 1250.00),
				new Producto("Mica comoda 5 cajones", 150.85),
				new Producto("TV Sony Bravia OLED", 2255.32),
				new Producto("Celular Samsung", 395.32)
				)
		.flatMap(producto -> {
			producto.setCreateAt(new Date()); //Crea la fecha al momento de crear el registro
			return dao.save(producto);
		})
		.subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
		
	}

}
