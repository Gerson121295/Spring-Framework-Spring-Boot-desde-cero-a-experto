package com.bolsadeideas.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente") //para evitar usar un input en el form que valide si el usuario ya fue creado si fue creado entonces se actualiza sino entonces se crea el registro.
public class ClienteController {
	
	@Autowired
	//@Qualifier("clienteDaoJPA") //para especifiar que IClienteDao utilizar en caso de que hubieran 2
	//private IClienteDao clienteDao; //Ya no se inyecta el IClienteDao ahora es IClienteService contiene los Daos
	private IClienteService clienteService;
	
	
	//Un debug para que muestre en consola los nombres de los directorios
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	//Constante para reemplazar la palabra "uploads"
	private final static String UPLOADS_FOLDER = "uploads";
	
	//Metodo para cargar imagen programaticamente en la respuesta HTTP
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
		log.info("pathFoto: " + pathFoto);
		
		Resource recurso = null;
		try {
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
		} catch (MalformedURLException e) {
	
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);	
	}
	
	
	//Metodo para ver el detalle del cliente por medio del id
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id); //Obtenemos el cliente
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
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {//por default estará en la pagina 0
		
		Pageable pageRequest = PageRequest.of(page, 4); //vamos a mostrar 4 registros por pagina
		Page<Cliente> clientes = clienteService.findAll(pageRequest); //clientes trae la lista paginada de los clientes
		
		PageRender<Cliente>	pageRender = new PageRender<>("/listar", clientes); //Pasamos cliente a nuestro PageRender quien hace los calculos de las paginas
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);//ahora en lugar clienteService se agrega clientes que ya trae la paginacion
		
		model.addAttribute("page", pageRender);//Pasamos el pageRender a la vista
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
				
				Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
				File archivo = rootPath.toFile();
				
				if(archivo.exists() && archivo.canRead()) {
					archivo.delete();
				}			
			}
			
			
			//Ruta interna del proyecto no se recomienda guardar imagenes o archivos dentro del proyecto, el proyecto solo debe ser de lectura.
			//se guarda en la ruta fisica de nuestro proyecto que estamos desarrollando (elcodigo fuente) y no el compilado jar (o war) por lo que se tiene que estar actualizando la carpeta de nuestro proyecto para ver las fotos
			//Path directorioRecursos = Paths.get("src//main//resources//static/uploads"); //aqui se guardarán las imagenes
			//String rootPath = directorioRecursos.toFile().getAbsolutePath(); //Obtenemos en string la ruta de las fotos
			
			//Ruta externa - separada al proyecto al jar o war (Para guardar las fotos y archivos - Es la forma recomendable)
			//String rootPath = "C://Temp//uploads"; //Crear la carpeta uploads en esa ruta //En linux la ruta es: "/opt/uploads";
			
			
			//Agregar directorio absoluto y externo en raiz del proyecto: 
			//Ruta Completa del proyecto: C:/User/Escritorio/spring-boot-data-jpa/uploads

			String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename); //Crear una nueva foto
			Path rootAbsolutPath = rootPath.toAbsolutePath();
			
			//Un debug para que muestre en consola los nombres de los directorios
			log.info("rootPath: " + rootPath); //Path relativo al proyecto.
			log.info("rootAbsolutPath: " + rootAbsolutPath); //Path absoluto: desde disco c hasta el nombre del archivo.
			
			try {
/*				//COdigo para Ruta externa al proyecto en disco C o ruta interna dentro del proyecto en carpeta static 
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				
				flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename()+ "'");			
				cliente.setFoto(foto.getOriginalFilename()); 	//Pasamos el nombre de la foto del cliente
*/				
				//Codigo para directorio absoluto y externo en raiz del proyecto
				Files.copy(foto.getInputStream(), rootAbsolutPath);
				flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename + "'");
				cliente.setFoto(uniqueFilename); 	//Pasamos el nombre de la foto del cliente
							
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con exito!" : "Cliente creado con éxito!"; //Valida si el id ya existe entonces el registro se edita si no se crea el registro.
		
		clienteService.save(cliente); //Utiliza el metodo save y se le envia el cliente a guardar //ahora en lugar de clienteDao es clienteService
		status.setComplete(); //Se destruye la sesion. 
		flash.addFlashAttribute("success", mensajeFlash); //muestra una notificacion al crear un registro
		return "redirect:listar"; //Luego de guardar el objeto redirecciona a la URL listar
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id > 0) {
			
			Cliente cliente = clienteService.findOne(id); //Eliminar archivo de imagen
			
			clienteService.delete(id); //ahora en lugar de clienteDao es clienteService
			flash.addFlashAttribute("success", "Cliente eliminado con exito!"); //muestra una notificacion al eliminar un registro
		
			//Eliminar archivo de imagen cuando se elimina el cliente
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
			File archivo = rootPath.toFile();
			
			if(archivo.exists() && archivo.canRead()) {
				if(archivo.delete()) {
					flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminado con exito!");
				}
			}
		}
		return "redirect:/listar";
	}
}








