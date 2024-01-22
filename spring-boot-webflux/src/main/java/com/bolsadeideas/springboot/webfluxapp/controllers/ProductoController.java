package com.bolsadeideas.springboot.webfluxapp.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsadeideas.springboot.webfluxapp.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webfluxapp.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	@Autowired
	private ProductoDao dao;
	
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@GetMapping({"listar", "/"})
	public String listar(Model model) {
		
		//Flux<Producto> productos = dao.findAll(); //Obtener todos los productos
		
		//Obtiene los productos y convierte el nombre de producto en mayusculas
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		productos.subscribe(prod -> log.info(prod.getNombre())); //Para imprimir en consola los productos
		
		model.addAttribute("productos", productos); //"productos" es la variable que une el controlador con la vista para pasar los datos de los productos
		model.addAttribute("titulo", "listado de productos"); //"titulo" es la variable que une el controlador con la vista para pasar el titulo "Listado de productos"
		return "listar";
	}
	
	
	//Modo Reactive Data Driven para manejar la contrapresión: va mostrando los productos de 2 en 2 mientras carga.
	@GetMapping("listar-datadriver")
	public String listarDataDriver(Model model) {
		
		//Flux<Producto> productos = dao.findAll(); //Obtener todos los productos
		
		//Obtiene los productos y convierte el nombre de producto en mayusculas
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		})
		
		.delayElements(Duration.ofSeconds(1));  //Tiempo que tarda la app en Cargar
		
		productos.subscribe(prod -> log.info(prod.getNombre())); //Para imprimir en consola los productos
		
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2)); //Va mostrando los productos en 2 en 2 mientras va cargando.  //"productos" es la variable que une el controlador con la vista para pasar los datos de los productos
		model.addAttribute("titulo", "listado de productos"); //"titulo" es la variable que une el controlador con la vista para pasar el titulo "Listado de productos"
		return "listar";
	}
	
	//Modo Chunked para manejar la contrapresión
	@GetMapping("listar-full")
	public String listarFull(Model model) {
		
		//Flux<Producto> productos = dao.findAll(); //Obtener todos los productos
		
		//Obtiene los productos y convierte el nombre de producto en mayusculas
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		})
				.repeat(100); // repite 100 veces el flujo, agrega 100 elementos
	
		model.addAttribute("productos", productos); //"productos" es la variable que une el controlador con la vista para pasar los datos de los productos
		model.addAttribute("titulo", "listado de productos"); //"titulo" es la variable que une el controlador con la vista para pasar el titulo "Listado de productos"
		return "listar";
	}
	
	//Modo Chunked para manejar la contrapresión
	@GetMapping("listar-chunked")
	public String listarChunked(Model model) {
		
		//Flux<Producto> productos = dao.findAll(); //Obtener todos los productos
		
		//Obtiene los productos y convierte el nombre de producto en mayusculas
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		})
				.repeat(100); // repite 100 veces el flujo, agrega 100 elementos
	
		model.addAttribute("productos", productos); //"productos" es la variable que une el controlador con la vista para pasar los datos de los productos
		model.addAttribute("titulo", "listado de productos"); //"titulo" es la variable que une el controlador con la vista para pasar el titulo "Listado de productos"
		return "listar-chunked";
	}
	
	
}





