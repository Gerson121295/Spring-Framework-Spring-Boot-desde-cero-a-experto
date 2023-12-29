package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Autowired
    private MessageSource messageSource;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required= false) String error, //para manejar los errores(al agregar usuario o password incorrecta
			@RequestParam(value="logout", required= false) String logout, //variable para notificar cuando se cierra sesion
			Model model, Principal principal, RedirectAttributes flash, Locale locale) {
		
		if(principal != null) {  //valida Si el usuairo ya ha iniciado sesion redirige a la pagina de inicio para evitar logearse otra vez
			//flash.addFlashAttribute("info", "Ya ha iniciado session anteriormente");
			flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/"; //redirige a la pagina principal
			}
		
		if(error != null) { //Si error es diferente de null pasa a la vista
			//model.addAttribute("error","Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
			model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
		}
		
		if(logout != null) {
			//model.addAttribute("success", "Ha cerrado sesion con exito."); //Notificacion cuando el usuario cierra sesion
			model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
		}
		
		return "login"; //Retorna la vista login
	}

}


/*
  
 -- Forma para crear un usuairos con contraseñas se envian por el formulario de registro de usuarios 
 (el proceso de encriptar la contraseña al momento de guardarla)
 
-- La inyectas en el controlador:

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
-- Luego ya la puedes encriptar en algún método del controlador @PostMapping:

	String password = usuario.getPassword();
	String bcryptPassword = passwordEncoder.encode(password);
	usuario.setPassword(bcryptPassword);
 
 */



