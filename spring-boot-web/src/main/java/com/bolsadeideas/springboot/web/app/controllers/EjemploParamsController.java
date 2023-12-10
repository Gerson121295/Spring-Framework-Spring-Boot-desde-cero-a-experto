package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;


@Controller 
@RequestMapping("/params")
public class EjemploParamsController {
	
	@GetMapping("/") //http://localhost:8080/params/
	public String index() {
		return "params/index";
	}
	
	@GetMapping("/string") //Probar: localhost:8080/params/string?texto="hola que tal"
	public String param(@RequestParam(name="texto", required=false, defaultValue="Algun valor...") String texto, Model model) { //Parametros que recibe, agregar (name="texto")  es opcional.  required=false (por defecto)
		model.addAttribute("resultado", "El resultado es: "+ texto); //"resultado" es la variable que conecta con la vista.
		return "params/ver"; //Vista dentro de llamada ver dentro de la carpeta params de la carpeta templates
	}
	
	//Se envian 2 parametros.
	@GetMapping("/mix-params") //Probar: localhost:8080/params/
	public String param(@RequestParam String saludo, @RequestParam Integer numero, Model model) { 
		model.addAttribute("resultado", "El saludo enviado es: "+ saludo + "' y el número es '" + numero + "'"); //"resultado" es la variable que conecta con la vista.
		return "params/ver"; //Vista dentro de llamada ver dentro de la carpeta params de la carpeta templates
	}

	
	//Se envian 2 parametros.
	@GetMapping("/mix-params-request") //Probar: localhost:8080/params/
	public String param(HttpServletRequest request, Model model) { //request nos da acceso a todos los paramettros
		String saludo = request.getParameter("saludo");
		Integer numero = null;
		try {
			numero = Integer.parseInt(request.getParameter("numero"));
		}catch(NumberFormatException e) {
			numero = 0;
		}
		
		model.addAttribute("resultado", "El saludo enviado es: "+ saludo + "' y el número es '" + numero + "'"); //"resultado" es la variable que conecta con la vista.
		return "params/ver"; //Vista dentro de llamada ver dentro de la carpeta params de la carpeta templates
	}
	
}








