package com.bolsadeideas.springboot.web.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.web.app.models.Usuario;


@Controller
@RequestMapping("/app")
public class IndexController {
	
	//Injectando valores usando Value desde application.properties
	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;
	
	@Value("${texto.indexcontroller.perfil.titulo}")
	private String textoPerfil;
	
	@Value("${texto.indexcontroller.listar.titulo}")
	private String textoListar;
	
	
	
	//pasar datos a la vista
	@GetMapping({"/index", "/", "", "/home"}) //@GetMapping("/index")   //@GetMapping(value="/index")   //@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model) { //Otra forma es usar ModelMap en lugar del Model //Otraforma usar Map:  String index(Map<String, Object> map){
		
		//model.addAttribute("titulo", "Hola spring Framework 00");  //Usando Map: map.put("titulo", "hola spring Framework");   //Sin inyeccion del titulo
		
		//Con Injeccion del titulo: Hola spring Framework 00
		model.addAttribute("titulo", textoIndex); //textoIndex es la variable que tiene el dato inyectado desde el archivo application.properties
		return "index";
	}
	

	@RequestMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();
		//Asignacio de nombre del usuario
		usuario.setNombre("Andres");
		usuario.setApellido("Pérez");
		usuario.setEmail("andres@correo.com");
		
		model.addAttribute("usuario", usuario); //"usuario" es el nombre de la variable en la vista. usuario es el objeto a mandar
		//Sin Injeccion 
		//model.addAttribute("titulo", "Perfil del usuario: ".concat(usuario.getNombre()));  //Usando Map: map.put("titulo", "hola spring Framework"); 
		
		//Con Injeccion del titulo: Perfil del usuario
		model.addAttribute("titulo", textoPerfil.concat(usuario.getNombre()));  //Usando Map: map.put("titulo", "hola spring Framework"); 
				
		
		return "perfil";
	}
	
	@RequestMapping("/listar")
	public String listar(Model model) {
		List<Usuario> usuarios = new ArrayList<>();
		// Creamos nuevos usuarios 
		usuarios.add(new Usuario("Andres", "Pérez", "andres@correo.com")); 
		usuarios.add(new Usuario("John", "Doe", "john@correo.com")); 
		usuarios.add(new Usuario("Jane", "Guzman", "jane@correo.com")); 
		
		
		 /* //Otra forma refactorizada de agregar los usuarios
		 List<Usuario> usuarios = Arrays.asList(
				 new Usuario("Andres", "Pérez", "andres@correo.com"),
				 new Usuario("John", "Doe", "john@correo.com"),
				 new Usuario("Jane", "Guzman", "jane@correo.com")
		 );
		 */
		 
		//model.addAttribute("titulo", "Listado de usuarios");	
		model.addAttribute("titulo", textoListar);
		
		model.addAttribute("usuarios", usuarios);
		return "listar";
	}
	
	//Otra forma de pasar datos a la vista index.html(forma de pasar datos a diferentes vistas)
	@ModelAttribute("usuarios")
	public List<Usuario> poblarUsuarios(){
		List<Usuario> usuarios = Arrays.asList(
				new Usuario("Andres", "Pérez", "andres@correo.com"),
				 new Usuario("John", "Doe", "john@correo.com"),
				 new Usuario("Jane", "Guzman", "jane@correo.com")
		 );
		
		return usuarios;
	}
	
	
	
	
}






