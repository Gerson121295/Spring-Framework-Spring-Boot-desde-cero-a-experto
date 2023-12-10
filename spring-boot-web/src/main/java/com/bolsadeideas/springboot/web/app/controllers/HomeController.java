package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Retornando redirect y forward como respuesta en metodos del controller

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		//return "redirect:/app/index"; //Al escribir localhost:8080 redirecciona a: http://localhost:8080/app/index 
		//return "redirect:https://www.google.com"; //Al escribir localhost:8080 redirecciona a google.com
	
		//Otra forma de redireccionar es usar forward: pero no cambia la ruta de la url 
		return "forward:/app/index";//Usado para rutas propias del proyecto
	}
	

}
