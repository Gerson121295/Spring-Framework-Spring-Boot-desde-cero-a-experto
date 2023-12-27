package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente") //para evitar usar un input en el form que valide si el usuario ya fue creado si fue creado entonces se actualiza sino entonces se crea el registro.
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	//@Qualifier("clienteDaoJPA") //para especifiar que IClienteDao utilizar en caso de que hubieran 2
	//private IClienteDao clienteDao; //Ya no se inyecta el IClienteDao ahora es IClienteService contiene los Daos
	private IClienteService clienteService;	
	
	@Autowired
	public IUploadFileService uploadFileService; //Inyeccion de la interfaz que contiene los metodos de la logica de subida de arhivo(imgs)
	
	//Metodo para cargar imagen programaticamente en la respuesta HTTP
	//@Secured({"ROLE_USER","ROLE_ADMIN"}) //Agregar seguridad en el controlador usando anotaciones @Secured SOLO LOS usuarios con rol  ROLE_USER y ROLE_ADMIN pueden acceder
	@Secured({"ROLE_USER"}) //Agregar seguridad en el controlador usando anotaciones @Secured SOLO LOS usuarios con rol  ROLE_USER y ROLE_ADMIN pueden acceder
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		
		//Implementacion de metodo (load) para cargar la foto de la interfaz IUploadFileService
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);	
	}
	
	
	//Metodo para ver el detalle del cliente por medio del id
	@PreAuthorize("hasAnyRole('ROLE_USER')") //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")  - Agregar seguridad en el controlador usando anotaciones @PreAuthorize SOLO LOS usuarios con rol  ROLE_USER pueden acceder
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		//El metodo findOne NO optimizado realiza 2 consultas
		//Cliente cliente = clienteService.findOne(id); //Obtenemos el cliente
		
		//Metodo Optimizado consultas JPQL en Clientes con JOIN FETCH para obtener las facturas. - Este metodo realiza 1 consulta
		Cliente cliente = clienteService.fetchByIdWithFacturas(id); //Obtenemos el cliente
		
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente); //Pasamos a la vista
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}
	
	
/*	//Metodo Listar clientes
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());//ahora en lugar de clienteDao es clienteService
		return "listar";
	}
*/	
	
	//Metodo Listar clientes con Paginacion
	@RequestMapping(value= {"/listar", "/"}, method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model, //por default estará en la pagina 0
			Authentication authentication,  //valida la autenticacion del usuario
			HttpServletRequest request ) { //chequea autorizacion con securityContextHolderAware
		
		if(authentication != null) { //validar si la autenticacion no es null
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}
			
		//Otra forma de inyectar el Authentication para evitar enviarlo como parametro
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) { //validar si la autenticacion no es null
			logger.info("Utilizando forma estatica SecurityContextHolder.getContext().getAuthentication(); - Usuario autenticado, username es: ".concat(auth.getName()));
		}

		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		}else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		
		//Otra forma - Chequear autorizacion con  SecurityContextHolderAwareRequest
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");//"ROLE_" es el sufijo del rol se puede dejar vacio: "" y ya en el if se escribe completo
		if(securityContext.isUserInRole("ADMIN")) { //if(securityContext.isUserInRole("ROLE_ADMIN")) {  //se puede validar completo si en la instancia se dejo vacio: ""
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		//Otra forma - Chequear autorizacion con HttpServletRequest request de forma nativa
		if(request.isUserInRole("ROLE_ADMIN")) {  //se puede validar con el nombre del rol completo
			logger.info("Forma usando HttpServletRequest request: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest request: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		Pageable pageRequest = PageRequest.of(page, 4); //vamos a mostrar 4 registros por pagina
		Page<Cliente> clientes = clienteService.findAll(pageRequest); //clientes trae la lista paginada de los clientes
		
		PageRender<Cliente>	pageRender = new PageRender<>("/listar", clientes); //Pasamos cliente a nuestro PageRender quien hace los calculos de las paginas
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);//ahora en lugar clienteService se agrega clientes que ya trae la paginacion
		
		model.addAttribute("page", pageRender);//Pasamos el pageRender a la vista
		return "listar";
	}
	
	
	//Metodo para insertar un nuevo registro - Utiliza otra forma para pasar la data a la vista
	@PreAuthorize("hasRole('ROLE_ADMIN')") //Agregar seguridad en el controlador usando anotaciones @PreAuthorize SOLO LOS usuarios con rol  ROLE_ADMIN pueden acceder
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {  //en lugar de Model model se uso Map
		
		Cliente cliente = new Cliente(); //Se instancia el cliente
		model.put("cliente", cliente);//En lugar de addAttribute se utilizo put para pasar la data a la vista
		model.put("titulo", "Formulario del cliente");
		return "form";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") //Agregar seguridad en el controlador usando anotaciones @PreAuthorize SOLO LOS usuarios con rol  ROLE_ADMIN pueden acceder
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash){
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id); //ahora en lugar de clienteDao es clienteService
			if(cliente == null) {
			flash.addFlashAttribute("error", "El ID del cliente no existe en la BD!"); //muestra notificacion al editar un registro
			return "redirect:/listar";	
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!"); //muestra notificacion al editar un registro
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";	
	}
	
	
	@Secured("ROLE_ADMIN") //Agregar seguridad en el controlador usando anotaciones @Secured SOLO LOS usuarios con rol  ROLE_ADMIN pueden acceder.
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model, 
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) { //@Valid y BindingResult - Valida los campos que se ingresen en los campos del cliente
		//el parametro @ModelAttribute("cliente") define el nombre de la vista a la que se pasara los datos, pero si La clase Cliente se llama igual que la vista cliente no es necesario agregar el @ModelAttribute("cliente")
		//Sessionstatus se agrega debido a la anotacion @SessionAttributes("cliente")
		//RedirectAttributes flash permite mostrar una notificacion al crear un registro
		if(result.hasErrors()) {//si result tiene errores
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}
		
		//Validacion de la foto
		if(!foto.isEmpty()){
			
			//Eliminar archivo de imagen cuando se edita la foto del cliente, se elimina la anterior y se agrega la nueva
			if(cliente.getId() != null && cliente.getId() > 0 
					&& cliente.getFoto() != null && cliente.getFoto().length() > 0 ) {
				
				uploadFileService.delete(cliente.getFoto());	
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename + "'");
			cliente.setFoto(uniqueFilename); 	//Pasamos el nombre de la foto del cliente
		}
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con exito!" : "Cliente creado con éxito!"; //Valida si el id ya existe entonces el registro se edita si no se crea el registro.
		
		clienteService.save(cliente); //Utiliza el metodo save y se le envia el cliente a guardar //ahora en lugar de clienteDao es clienteService
		status.setComplete(); //Se destruye la sesion. 
		flash.addFlashAttribute("success", mensajeFlash); //muestra una notificacion al crear un registro
		return "redirect:listar"; //Luego de guardar el objeto redirecciona a la URL listar
	}
	
	
	@Secured("ROLE_ADMIN") //Agregar seguridad en el controlador usando anotaciones @Secured SOLO LOS usuarios con rol  ROLE_ADMIN pueden acceder.
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id > 0) {
			
			Cliente cliente = clienteService.findOne(id); //Eliminar archivo de imagen
			
			clienteService.delete(id); //ahora en lugar de clienteDao es clienteService
			flash.addFlashAttribute("success", "Cliente eliminado con exito!"); //muestra una notificacion al eliminar un registro
		
			//Eliminar archivo de imagen cuando se elimina el cliente

				if(uploadFileService.delete(cliente.getFoto())) {
					flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminado con exito!");
				}
		}
		return "redirect:/listar";
	}
	
	
	//Obteniendo programáticamente el role (s) del usuario en el controlador
	
	private boolean hasRole(String role) {
		
		// Inyectar el Authentication
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		//return authorities.contains(new SimpleGrantedAuthority(role)); //otra forma. el metodo constains(grantedAuthority) retorna un booleano(true o false) si o no contiene el elemento en la coleccion
		
		//Esta es otra forma la ventaja es que recorremos todo y obtenemos el rol y nos permite usar el logger
		for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		return false;
	}


}






