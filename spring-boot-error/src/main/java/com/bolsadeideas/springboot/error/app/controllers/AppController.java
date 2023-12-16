package com.bolsadeideas.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bolsadeideas.springboot.error.app.Services.UsuarioService;
import com.bolsadeideas.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

@Controller
public class AppController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/index")
	public String index() {
		
		//Integer valor = 100/0; //Error - ArithmeticException    //generica.hml funciona igual
		Integer valor = Integer.parseInt("10xx"); //Error - NumberFormatException   //generica.hml funciona igual
		
		return "index";
	}

/*
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
		Usuario usuario = usuarioService.obtenerPorId(id);
		
		if(usuario==null) {
			throw new UsuarioNoEncontradoException(id.toString());
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: ".concat(usuario.getNombre()));
		return "ver";
		
	}
*/	
	//Lanzamiento de exepcion usando API optiona de java 8 - Metodo obtener por id usando optional
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
			
		Usuario usuario = usuarioService.obtenerPorIdOptional(id).orElseThrow(() -> 
		new UsuarioNoEncontradoException(id.toString()));
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: ".concat(usuario.getNombre()));
		return "ver";
		
	}
	
	
	
}




