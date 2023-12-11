package com.bolsadeideas.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

import com.bolsadeideas.springboot.di.app.models.domain.Factura;

@Controller
@RequestMapping("/factura")
public class FacturaController {
	
	//Injectamos Factura
	@Autowired
	private Factura factura;
	
	@GetMapping("/ver")
	public String ver(Model model) {
		model.addAttribute("factura", factura); //Enviamos a la vista usando la variable "factura", y se le envia el objeto factura 
		model.addAttribute("titulo","Ejemplo Factura con inyeccion de dependencia");
	
		return "factura/ver";
	}

}





