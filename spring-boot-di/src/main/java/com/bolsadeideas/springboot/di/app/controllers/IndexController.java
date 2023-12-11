package com.bolsadeideas.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.di.app.models.service.IServicio;


@Controller
public class IndexController {
	
	//private MiServicio servicio = new MiServicio(); //Forma 1 de inyectar dependencia. (Aun no tenia implementada la interfaz la clase MiServicio)

	//@Autowired
	//private MiServicio servicio; //Forma 2 de inyectar dependencia. (Aun no tenia implementada la interfaz la clase MiServicio)
	
	//Es recomendable usar intefaces para la inyeccion de dependencia, para no inyectar una clase concreta.
	@Autowired
	@Qualifier("miServicioComplejo") //Para especificar que servicio será inyectado. Cada servicio se le dio un nombre.
	private IServicio servicio; //Forma 3 de inyectar dependencia. (Se implementa la interfaz Iservicio a la clase MiServicio)
		
/*	
	//Forma 4: Injeccion de dependencia via constructor y métodos setters
		//Via métodos setters
		@Autowired
		public void setServicio(IServicio servicio) {
			this.servicio = servicio;
		}
	
		//via constructor 
		@Autowired
		public IndexController(IServicio servicio) {
			this.servicio = servicio;
		}
*/
		
	@GetMapping({"/","","/index"})
	public String index(Model model) { //Model para pasar datos a la vista
		model.addAttribute("objeto", servicio.operacion()); //Muesta a la vista. Objeto es la variable de la vista, y servicio.operacion es la data que se envia.
		return "index";
	}

	
	
	
	
	
}



