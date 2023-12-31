package com.bolsadeideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;

@RestController //@RestController incluye @Controller @ResponseBody
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;	

	//Metodo handler que responderá con formato JSON
	//Metodo Listar clientes con Paginacion - API REST: Forma usando anotacion @ResponseBody sobre el metodo Handler
/*	@GetMapping(value="/listar-rest")
	public @ResponseBody List<Cliente> listarRest(){ //@ResponseBody indica que el listado de cliente se va a almacenar en el cuerpo de la respuesta y al guardarse spring asume que es un Rest puede ser un JSON o xml
		return clienteService.findAll(); 
	}
*/
	
	//Metodo handler que responderá con formato JSON y XML : http://localhost:8080/api/clientes/listar?format=xml   // http://localhost:8080/api/clientes/listar?format=json //http://localhost:8080/api/clientes/listar
	//Metodo Listar clientes con Paginacion - API REST: Forma usando anotacion @ResponseBody sobre el metodo Handler
	@GetMapping(value="/listar")
	//public @ResponseBody ClienteList listar(){ //@ResponseBody indica que el listado de cliente se va a almacenar en el cuerpo de la respuesta y al guardarse spring asume que es un Rest puede ser un JSON o xml //No es necesario agregar ResponseBody porque viene incluido en @RestController.
	public ClienteList listar(){
		return new ClienteList(clienteService.findAll()); 
	}
	
}





