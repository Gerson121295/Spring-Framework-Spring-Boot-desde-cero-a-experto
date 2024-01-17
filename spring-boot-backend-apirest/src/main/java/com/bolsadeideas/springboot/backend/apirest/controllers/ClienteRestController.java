package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

import jakarta.validation.Valid;

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
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		//Manejar problemas que pueden darse en la BD
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findById(id); //Busca  al cliente y lo guarda en cliente
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Si el cliente a buscar no existe Retorna un NOT_FOUND
		if(cliente == null) { 
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la BD")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK); //Retorna el cliente buscado y codigo 200 de OK
	}
	
	//Guardar o crear cliente : Metodo POST - http://localhost:8080/api/clientes  ->  { "nombre": "Turco",  "apellido":"Reu", "email": "turquin@gmail.com" }
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid  //valid va al inicio para validaciones definidas en la clase Cliente @Empty, @Email
			@RequestBody Cliente cliente,
			BindingResult result //para saber si ocurrio un problema en la validacion. El bindingResult va despues de la clase Cliente a validar,
			) {
		//cliente.setCreateAt(new Date()); //para agregar la fecha de creacion cuando se crea el cliente, otra forma de agregar una fecha es en la clase cliente lo cual se hizo.
	
		//Manejar problemas que pueden darse en la BD
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		
		//Validar si contiene errores los datos del cliente
		if(result.hasErrors()) { //si hay errores
			
			//Forma 1: Usando For para recorrer los campos
			/*List<String> errors = new ArrayList<>(); //lista errors contendra los errores
			
			for(FieldError err: result.getFieldErrors()) { //recorremos los campos
				errors.add("El campo '" + err.getField()+ "' " + err.getDefaultMessage()); //si hay error lo agrega a la lista errors
			}
			*/
			
			//Forma 2: Usando Stream
			List<String> errors = result.getFieldErrors() //convertir esta lista FieldErrors en un stream
					.stream()
					.map(err -> { //opcional: Podemos quitar las llaves y el return
						return "El campo '" + err.getField()+ "' " + err.getDefaultMessage();
						})
					//Ahora convertimos el Stream en una lista del tipo String
					.collect(Collectors.toList());
			
			response.put("errors", errors); //muestra los mensajes de error
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
				
		try {
			clienteNew = clienteService.save(cliente); //Crea o guarda  al cliente en la BD
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Retornamos un MAP con un JSON que contiene mensaje y el cliente.
		response.put("mensaje", "El cliente ha sido creado con exito!");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	//Editar un cliente  :  Metodo PUT - http://localhost:8080/api/clientes/3   ->  {"nombre": "Ivan", "apellido": "Godinez", "email": "ivas@gmail.com" }
	@PutMapping("/clientes/{id}") 
	public  ResponseEntity<?> update(@Valid  //valid va al inicio, para validaciones definidas en la clase Cliente @Empty, @Email
			@RequestBody Cliente cliente, 
			BindingResult result, //El bindingResult va despues de la clase Cliente a validar, para saber si ocurrio un problema en la validacion
			@PathVariable Long id
			) {
		
		Cliente clienteActual = clienteService.findById(id); //buscamos el cliente a modificar por id  y lo guardamos en clienteActual 
		
		Cliente clienteUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		
		//Validar si contiene errores los datos del cliente
		if(result.hasErrors()) { //si hay errores
					
			//Forma 1: Usando For para recorrer los campos
			/*List<String> errors = new ArrayList<>(); //lista errors contendra los errores
					
			  for(FieldError err: result.getFieldErrors()) { //recorremos los campos
					errors.add("El campo '" + err.getField()+ "' " + err.getDefaultMessage()); //si hay error lo agrega a la lista errors
				}
			*/
					
			//Forma 2: Usando Stream
			List<String> errors = result.getFieldErrors() //convertir esta lista FieldErrors en un stream
				.stream()
				.map(err -> { //opcional: Podemos quitar las llaves y el return
				return "El campo '" + err.getField()+ "' " + err.getDefaultMessage();
				})
			//Ahora convertimos el Stream en una lista del tipo String
				.collect(Collectors.toList());
					
				response.put("errors", errors); //muestra los mensajes de error
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				}
		
		
		//Si el cliente a buscar no existe Retorna un NOT_FOUND
		if(clienteActual == null) { 
			response.put("mensaje", "No se pudo editar el cliente ID: ".concat(id.toString().concat(" no existe en la BD")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		//Modificamos los datos del cliente actual
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			
			clienteUpdated = clienteService.save(clienteActual); //Actualizacion del cliente
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con exito!");
		response.put("cliente", clienteUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	//Eliminar un cliente : Metodo DELETE - http://localhost:8080/api/clientes/1
	@DeleteMapping("/clientes/{id}") 
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			clienteService.delete(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}






