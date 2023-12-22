package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura") //Importante mantener el objeto factura en una sesion mientras se procesa el formulario hasta que se envie al metodo guardar el metodo guardar procesa 
//y guarda persiste la factura y sus lineas en la BD, es importante darle el mismo nombre con el que se pasa a la vista en este caso es "factura", al guardar factura en la BD se completa el estatus y lo elimina en la session.
//Con esto factura y sus datos se mantiene persistente en la sesion hasta que sea guardada en la BD luego sera eliminada de la sesion con: status.complete(). Como esta en ClienteController
public class FacturaController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id,
			Model model,
			RedirectAttributes flash) {
		
		//Consultas JPQL en Facturas No optimizado realiza 7 consultas
		//Factura factura = clienteService.findFacturaById(id); //obtener la factura por el id
			
		//Optimizando consultas JPQL en Facturas JOIN FETCH para obtener los items: Solo hace 1 consulta, al no optimizarlo realiza 7 consultas por lo que afecta el rendimiento
		Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
		
		if(factura == null ) {//Valida si la factura es null, si es vacia manda un mensaje al usuario
			flash.addFlashAttribute("error", "La factura no existe en la BD");	//envia una notificacion de error
			return "redirect:/listar"; //si no existe la factura hace un redirect a la ruta de listar
		}
		
		//Si la factura existe la pasamos a la vista
		model.addAttribute("factura", factura); //"factura" es la variable que une a la vista y al controlador y factura es el objeto que se envia.
		model.addAttribute("titulo", "Factura : ".concat(factura.getDescripcion()));
		
		return "factura/ver"; //retorna un String el nombre de la vista ver
	}

	
	//Para hacer un debug
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/form/{clienteId}") //se debera pasar el id del cliente en la ruta para obtener la factura
	public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String, Object> model, 
			RedirectAttributes flash) { //RedirectAttributes para mostrar mensajes
		
		Cliente cliente = clienteService.findOne(clienteId); //Obtenemos el cliente por el id y se guarda en cliente
				
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD"); //Mostramos el mensaje de error
			return "redirect:/listar"; //Si el cliente es nulo redirigimos a la ruta listar
		}
		
		Factura factura = new Factura(); //Instanciamos Factura
		factura.setCliente(cliente); //Relacionamos una factura con el cliente
		
		//mandamos la factura a la vista del formulario
		model.put("factura", factura);//"factura" es la variable que conecta el controller con la vista y factura es el objeto que se envia
		model.put("titulo", "Crear Factura"); // "titulo" es la variable que conecta el controller con la vista y "Crear Factura" es el texto del titulo que se envia a la vista
		
		return "factura/form";//Ruta de la vista
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces = {"application/json"}) //term es el parametro que contiene el texto que se envia para buscar el producto, produce una salida json y la guarda en el ResponseBody
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		//return clienteService.findByNombre(term); //Forma 1 de hacer la consulta JPA para buscar productos 
		return clienteService.findByNombreLikeIgnoreCase(term); //Forma 2 de hacer la consulta JPA para buscar productos 
	}
	
	
	//Metodo para guardar los datos de la factura: datos que recibe esta en plantilla-items.html
	@PostMapping("/form")
	public String guardar(@Valid Factura factura,//factura es el objeto formulario que se inyecta automatica que contiene todos los metodos del formulario
		BindingResult result, //La anotacion @Valid(requerido xq en la clase Factura se le agrego anotacion de validacion NotEmpty al campo) y BindingResult(para ver si hay errores en la validacion)
		Model model, //para pasar datos a la vista
		@RequestParam(name="item_id[]", required=false) Long[] itemId,
		@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,
		RedirectAttributes flash,
		SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura"); //"titulo" variable que une a la vista y controller, "Crear Factura" es el titulo que se envia a la vista
			return "factura/form"; //retorna la vista form en factura
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura"); //"titulo" variable que une a la vista y controller, "Crear Factura" es el titulo que se envia a la vista
			model.addAttribute("error", "Error: La factura NO puede no tener líneas!");
			return "factura/form";
		}
		
		for(int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]); //encontrar el produto por id y guardarlo en producto
			
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			
			//debug para mostrar en consola el valor del id y la cantidad
			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
		
		//Guardar la factura en la BD
		clienteService.saveFactura(factura);
		
		status.setComplete(); //luego de guardar la factura en la BD eliminamos la factura de la sesion
		flash.addFlashAttribute("success", "Factura creada con exito!");
		return "redirect:/ver/" + factura.getCliente().getId();	//Redirige a ver el detalle del cliente por medio de su id.
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
		if(factura != null) { //Si factura no es igual a null se elimina.
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito!");
			return "redirect:/ver/" + factura.getCliente().getId();
		}
		
		//Si es null muestra un mensaje de error
		flash.addFlashAttribute("error", "La factura no existe en la BD, no se pudo eliminar!");
		return "redirect:/listar"; //Redirecciona al listado de clientes
	}
	
	
}

