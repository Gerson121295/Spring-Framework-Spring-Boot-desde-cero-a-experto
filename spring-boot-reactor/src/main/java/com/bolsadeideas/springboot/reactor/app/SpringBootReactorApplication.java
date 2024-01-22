package com.bolsadeideas.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner{ //interfaz CommandLineRunner para hacer la aplicacion de linea de comando - consola

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	
	/* El observable es el flux o publicador del evento, y el observador es el código que va dentro de la 
	 * expresión lambda en el método subscribe, se refiere a suscribir un observador o alguna tarea cuando 
	 * los datos estén disponibles y se emitan en el flujo reactivo.
	 */
	
	@Override
	public void run(String... args) throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
			usuariosList.add("Andres Guzman");
			usuariosList.add("Pedro Fulana");
			usuariosList.add("Maria Sultana");
			usuariosList.add("Diego Fulana");
			usuariosList.add("Juan Sutana");
			usuariosList.add("Bruc Lee");
			usuariosList.add("Bruce willis");
		
		//Creando un Flux(Observable) a partir de un List o Iterable
		Flux<String> nombres = Flux.fromIterable(usuariosList);
		
		//Flux<Usuario> nombres = Flux.just("Andres Guzman", "Pedro Fulana", "Maria Sultana", "Diego Fulana", "Juan Sutana", "Bruc Lee", "Bruce willis")  //Crear el primer flux(observable)

		//Flux<String> nombres = Flux.just("Andres Guzman", "Pedro Fulana", "Maria Sultana", "Diego Fulana", "Juan Sutana", "Bruc Lee", "Bruce willis");  //flux(observable) que se utiliza en el ejemplo

		//.doOnNext(elemento -> System.out.println(elemento)); //Forma1: Cuando es una linea de codigo.
		
		/* .doOnNext(elemento -> {  //Forma 2: Cuando es mas de una linea de codigo usar llaves.
			System.out.println(elemento);
			}); 
		*/
			//Forma 3: mas simple
			//.doOnNext(System.out::println);
			
			//Operador Map - El nombre paso a mayuscula
			//.map(nombre -> new Usuario(nombre.toUpperCase(), null))	//Asignamos que nombre será un objeto Usuario
		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))	//Asignamos que nombre será un objeto Usuario, split hace un corte cuando encuentre espacio vacio y lo guarda en la posicion 0 del array, lo mismo con el apellido, el apellido es la posicon 1 del array
			//Operador Filter
			//.filter(usuario -> {return true} //pasarian todos los objetos
				.filter(usuario -> {return usuario.getNombre().toLowerCase().equals("bruce");})	//busca nombre sea igual a bruce retorna un boolean si lo encuentra.  //o tambien usar: usuario.getNombre().equalsIgnoreCase("bruce")
			
			/* .map(nombre -> {		
				return nombre.toUpperCase(); //El nombre paso a mayuscula
			})  */
			
			//Emulando error que podrian suceder 		
				.doOnNext(usuario -> {
					if(usuario == null ) {  //usar para validar String si esta vacio:  e.isEmpty()  -  Usar para validar objeto: e == null 
						throw new RuntimeException("Nombres no pueden ser vacios");
					}
					//System.out.println(usuario.getNombre());
					System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
				
				}) //;   //Ejemplo de Map para recorrer los datos y modificarlos(ponerlo en mayuscula)
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase(); //El nombre paso a minuscula
					usuario.setNombre(nombre);
					return usuario;
			});
			
			
				
		//No sucede nada si no nos suscribimos - Al suscribirse ya muestra los nombres
		//nombres.subscribe();
		
		//Al suscribirnos estamos observando, un consumidor que ejecuta algun tipo de tarea que esta consumiendo cada elemento que esta emitiendo el observable
		//nombres.subscribe(log::info); //suscribimos y dentro del subscribe consumiremos el observable y se mostrará los nombres en consola
		//nombres.subscribe(e -> log.info(e)); //Con expresion Lamba
		usuarios.subscribe(e -> log.info(e.toString()),// e.getNombre()), //e.toString())
				error -> log.error(error.getMessage()), //Con expresion Lamba manejando el error
				
				//Evento onComplete - Ejcuta una tarea luego de terminar la ejecucion del flux
				new Runnable() {
					
					@Override
					public void run() {
						log.info("Ha finalizado la ejecucion del observable con éxito");
						
					}
				});
	}

	
}








