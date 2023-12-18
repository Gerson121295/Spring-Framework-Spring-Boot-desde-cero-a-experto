package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente") //para evitar usar un input en el form que valide si el usuario ya fue creado si fue creado entonces se actualiza sino entonces se crea el registro.
public class ClienteController {
	
	@Autowired
	//@Qualifier("clienteDaoJPA") //para especifiar que IClienteDao utilizar en caso de que hubieran 2
	//private IClienteDao clienteDao; //Ya no se inyecta el IClienteDao ahora es IClienteService contiene los Daos
	private IClienteService clienteService;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());//ahora en lugar de clienteDao es clienteService
		return "listar";
	}
	
	//Metodo para insertar un nuevo registro - Utiliza otra forma para pasar la data a la vista
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {  //en lugar de Model model se uso Map
		
		Cliente cliente = new Cliente(); //Se instancia el cliente
		model.put("cliente", cliente);//En lugar de addAttribute se utilizo put para pasar la data a la vista
		model.put("titulo", "Formulario del cliente");
		return "form";
	}
	
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id")Long id, Map<String, Object> model){
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id); //ahora en lugar de clienteDao es clienteService
		} else {
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";	
	}
	
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model, SessionStatus status) { //@Valid y BindingResult - Valida los campos que se ingresen en los campos del cliente
		//el parametro @ModelAttribute("cliente") define el nombre de la vista a la que se pasara los datos, pero si La clase Cliente se llama igual que la vista cliente no es necesario agregar el @ModelAttribute("cliente")
		//Sessionstatus se agrega debido a la anotacion @SessionAttributes("cliente")
		
		if(result.hasErrors()) {//si result tiene errores
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}
		
		clienteService.save(cliente); //Utiliza el metodo save y se le envia el cliente a guardar //ahora en lugar de clienteDao es clienteService
		status.setComplete(); //Se destruye la sesion. 
		return "redirect:listar"; //Luego de guardar el objeto redirecciona a la URL listar
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id) {
		if(id > 0) {
			clienteService.delete(id); //ahora en lugar de clienteDao es clienteService
		}
		return "redirect:/listar";
	}
}








