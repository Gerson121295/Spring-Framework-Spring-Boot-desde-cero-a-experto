package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

//CORS sig: Intercambio de recursos de origen cruzado  - Permite a los navegadores enviar y recibir datos restringidos (script, archivos de un dominio a otro diferente)
//CORS (Cross-Origin Resource Sharing) es un mecanismo que, a través de las cabeceras de los encabezados HTTP, va a permitir a un determinado cliente (User-Agent) a acceder a los recursos de un servidor diferente al del servidor actual

//@CrossOrigin(origins= {"http://localhost:4200/", "*"}) //Con esto le damos acceso a este dominio "http://localhost:4200" para que pueda solicitar o enviar datos. Al no especificar a que metodos tiene acceso el servidor, tendra acceso a todos.
//@CrossOrigin(origins = {"http://localhost:4200", "*"})
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){	
		return clienteService.findAll();	
	}
	
}






