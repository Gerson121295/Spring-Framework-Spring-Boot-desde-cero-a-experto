package com.bolsadeideas.springboot.form.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")  //La anotacion @SessionAttributes para mantener los datos durante el ciclo del formulario, se envia del parametro de la vista usuario para mantener los datos persistentes: Agregar:SessionStatus status, status.setComplete().
public class FormController {
	
	@Autowired
	private UsuarioValidador validador; //Injectamos la clase que valida los campos(identificador2 y nombre)
	
	@Autowired
	private PaisService paisService; //Injectamos la interfaz PaisService
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolesEditor roleEditor;
	
	//Otra forma de validar para no usar en el metodo procesar del Post: la linea: validador.validate(usuario, result); binder utiliza la anotacion valid del metodo procesar
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador); // binder.setValidator(validador);  Solo valida las validaciones de la clase UsuarioValidador. Con  addValidators permite agregar validaciones desde la clase usuario, UsuarioValidador, y del archivo properties.
	
		//Formateando fechas con @InitBinder
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); //Forma 1 general
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true)); //Especifica al campo
		
		//Convierte los campos de la clase usuario en el form en mayuscula y quita los espacios
		//binder.registerCustomEditor(String.class, new NombreMayusculaEditor()); //String.class tipo de dato del campo a cambiar, 
		
		//Convierte el campo nombre de la clase usuario en el form en mayuscula y quita los espacios
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor()); //String.class tipo de dato del campo a cambiar, 
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor()); //String.class tipo de dato del campo a cambiar, 
		
		//Obtiene el pais
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);   //Pais.class el tipo de dato del campo
		
		//Role
		binder.registerCustomEditor(Role.class, "roles", roleEditor);   //Pais.class el tipo de dato del campo
		
	}
	
	//Agregar el genero usando Lista.
	@ModelAttribute("genero")
	public List<String> genero(){
		return Arrays.asList("Hombre", "Mujer");
	}
	
	
	//Clase para pasar los paises a la vista del campo pais -usando pais como String en clase usuario
	@ModelAttribute("paises")//Con ModelAttribute establecemos el nombre "paises" para pasar la data a la vista
	public List<String> paises(){
		return Arrays.asList("España","Mexico","Chile","Argentina","Perú","Colombia", "Venezuela");
	}
	
/*	
	//Clase para pasar los paises a la vista del campo pais -usando pais como Objeto Pais en clase usuario
	@ModelAttribute("listaPaises")//Con ModelAttribute establecemos el nombre "paises" para pasar la data a la vista
	public List<Pais> listaPaises(){
		return Arrays.asList(
				new Pais(1,"ES", "España"),//Instanciamos pais y le llenamos sus atributos
				new Pais(2,"MX", "Mexico"),
				new Pais(3,"CL", "Chile"),
				new Pais(4,"AR", "Argentina"),
				new Pais(5,"PE", "Perú"),
				new Pais(6,"CO", "Colombia"),
				new Pais(7,"VE", "Venezuela")
				);		
	}
*/	
	
	//Pasar los paises a la vista del campo pais -usando la interfaz PaisServie
	@ModelAttribute("listaPaises")//Con ModelAttribute establecemos el nombre "paises" para pasar la data a la vista
	public List<Pais> listaPaises(){
		return paisService.listar(); //LLama el metodo listar de la interfaz PaisService	
	}
	
	
	//Pasar los roles a la vista del campo roles - usando la interfaz PaisServie
	@ModelAttribute("listaRolesString")//Con ModelAttribute establecemos el nombre "paises" para pasar la data a la vista
	public List<String> listaRolesString(){
			List<String> roles = new ArrayList();
			roles.add("ROLE_ADMIN");
			roles.add("ROLE_USER");
			roles.add("ROLE_MODERATOR");
			return roles;
	}
	
	
	//Clase para pasar los roles a la vista del campo roles LLenando la lista select con Map
		@ModelAttribute("listaRolesMap")//Con ModelAttribute establecemos el nombre "paisesMap" para pasar la data a la lista
		public Map<String, String> listaRolesMap(){
			Map<String, String> roles = new HashMap<String, String>(); //Map es la interfaz y HashMap es la implementacion
			roles.put("ROLE_ADMIN", "Administrador");//key="ES" es el que envia la vista al controller, Values="España" es lo que muestra la vista
			roles.put("ROLE_USER", "Usuario");
			roles.put("ROLE_MODERATOR", "Moderador");
			return roles;
		}
	
	
		//Lista roles del Objeto
		@ModelAttribute("listaRoles")
		public List<Role> listaRoles(){
			return this.roleService.listar();
		}
		
	
	//Clase para pasar los paises a la vista del campo pais LLenando la lista select con Map
	@ModelAttribute("paisesMap")//Con ModelAttribute establecemos el nombre "paisesMap" para pasar la data a la lista
	public Map<String, String> paisesMap(){
		Map<String, String> paises = new HashMap<String, String>(); //Map es la interfaz y HashMap es la implementacion
		paises.put("ES", "España");//key="ES" es el que envia la vista al controller, Values="España" es lo que muestra la vista
		paises.put("MX", "Mexico");
		paises.put("CL", "Chile");
		paises.put("AR", "Argentina");
		paises.put("PE", "Perú");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		return paises;
	}
	
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Gerson");
		usuario.setApellido("Ep");
		usuario.setIdentificador("123.456.789-K"); //No se incluye en el formulario para ver como se persiste usando Session Attribute
		usuario.setHabilitar(true); ////Checkbox queda true activado
		usuario.setValorSecreto("Algun valor secreto ***** ");
		usuario.setPais(new Pais(3, "CL", "Chile")); //Para que por defecto aparezca seleccionado el pais de Chile en la lista
		usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER"), new Role(3, "Moderador", "ROLE_MODERATOR"))); //Para que por defecto aparezca seleccionado el rol usuario en la lista
		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("usuario", usuario); //"user" une a la vista, y le enviamos el objeto usuario
		return "form";
	}
	
	
	/*
	//Otra forma de pasar los datos a la vista
	@PostMapping("/form")
	public String procesar(Model model, 
			//Capturar los campos para luego pasarlos al formulario
			@RequestParam(name="username") String username, //username es como esta definida en la vista form: otra forma es usar name o value para definir como esta escrito en form.html
			@RequestParam String password, //password es la palabra como esta definida en la vista del formulario "form" name="password"
			@RequestParam String email) {
		
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(password);
		
		model.addAttribute("titulo", "Resultado form");	
		model.addAttribute("username", username); //"username" une a la vista del Form con el controler, username Envia el valor de la variable username
		model.addAttribute("password", password);
		model.addAttribute("email", email);
		
		return "resultado";
	}
	*/
	
	
	//Otra forma de pasar los datos a la vista es pasar un objeto 
	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model) { //, SessionStatus status) {  //Se envia el objeto usuario, Model hace referencia a la vista. 
		//agregar despues del @valid @ModelAttribute("user") sirve para especificar que la vista que une los campos con el controller se llame "user"
		//BindingResult: Contiene los mensaje de error en caso de que ocurra error en las validaciones. El BindingResult debe estar despues del objeto que se valide. Y el objeto debe ser el primero en ser agregado.
		
		//validador.validate(usuario, result); //result es de clase error  //No es necesario porque se agrego el metodo: initBinder
			
		/*
		//Forma manual de obtener los errores
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> { //Recorre todos los campos del formulario
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));  //err.getField() obtiene el campo que dio el error. y el siguiente argumento da el mensaje del error.
			});
			model.addAttribute("error", errores);
			return "form";
		}
		*/
		
		//Forma automatica de obtener los errores
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Resultado form");
			return "form";
		}
		
		//model.addAttribute("usuario", usuario); //"usuario" une a la vista, y le enviamos el objeto usuario
		//status.setComplete();//completar este proceso   //se corta y se pega al metodo get /ver porque se corto de este metodo SessionStatus status y se pego en el metodo get /ver
		return "redirect:/ver";	//return "resultado"; //antes erá ahora redirige al metodo get ver
	}
	
	
	//Redirect después del POST procesar. Para que cuando se de clic en actualizar al navegador no solicite que se reenviará el formulario, si no que se haga automaticamente
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
		
		//Si el usuario es nulo redirige al /form
		if(usuario == null) {
			return "redirect:/form";
		}
		
		model.addAttribute("titulo", "Resultado form");	
		status.setComplete();//completar este proceso 
		return "resultado";
	}
	
	
	
}






