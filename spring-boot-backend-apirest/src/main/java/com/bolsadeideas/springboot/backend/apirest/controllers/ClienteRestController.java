package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	//Mostrar todos los clientes : Metodo GET - http://localhost:8080/api/clientes
	@GetMapping("/clientes")
	public List<Cliente> index(){	
		return clienteService.findAll();	
	}
	
	//Buscar a un cliente por id : Metodo GET - http://localhost:8080/api/clientes/1
	@GetMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK) //Retorna ok 200 el estado cuando encuentra el cliente
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}
	
	//Guardar o crear cliente : Metodo POST - http://localhost:8080/api/clientes  ->  { "nombre": "Turco",  "apellido":"Reu", "email": "turquin@gmail.com" }
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED) //Retorna code 201 ok el estado cuando se crea el cliente
	public Cliente create(@RequestBody Cliente cliente) {
		//cliente.setCreateAt(new Date()); //para agregar la fecha de creacion cuando se crea el cliente, otra forma de agregar una fecha es en la clase cliente lo cual se hizo.
		return clienteService.save(cliente);
	}
	
	//Editar un cliente  :  Metodo PUT - http://localhost:8080/api/clientes/3   ->  {"nombre": "Ivan", "apellido": "Godinez", "email": "ivas@gmail.com" }
	@PutMapping("/clientes/{id}") 
	@ResponseStatus(HttpStatus.CREATED) //Retorna code 201 ok el estado cuando se actualiza el cliente
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id); //buscamos el cliente a modificar por id  y lo guardamos en clienteActual 
		
		//Modificamos los datos del cliente actual
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		
		return clienteService.save(clienteActual);//Realizamo la actualizacion de los datos del clienteActual(que fue buscado por id)
	}
	
	//Eliminar un cliente : Metodo DELETE - http://localhost:8080/api/clientes/1
	@DeleteMapping("/clientes/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) //Retorna NO_CONTENT code 204 el estado cuando se elimina el cliente
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
	
	
}






