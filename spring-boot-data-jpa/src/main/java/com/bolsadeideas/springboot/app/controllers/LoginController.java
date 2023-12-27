package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required= false) String error, //para manejar los errores(al agregar usuario o password incorrecta
			@RequestParam(value="logout", required= false) String logout, //variable para notificar cuando se cierra sesion
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {  //valida Si el usuairo ya ha iniciado sesion redirige a la pagina de inicio para evitar logearse otra vez
			flash.addFlashAttribute("info", "Ya ha iniciado session anteriormente");
			return "redirect:/"; //redirige a la pagina principal
			}
		
		if(error != null) { //Si error es diferente de null pasa a la vista
			model.addAttribute("error","Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesion con exito."); //Notificacion cuando el usuario cierra sesion
		}
		
		return "login"; //Retorna la vista login
	}

}
