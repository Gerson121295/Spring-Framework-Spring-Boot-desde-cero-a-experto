package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Usuario {
	
	private String identificador;
	
/*	//Validacion personalidad con expresiones regulares: REGEX: [2numeros del 0-9][punto o coma][3numeros del 0-9][punto][3numeros del 0-9][un Guion - ][una letra Mayuscula].
	@Pattern(regexp = "[0-9]{2}[,.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")  //[\\d]es equivalente a [0-9]  El patron debe ser: 12.456.789-K
	private String identificador2;
	
	//@NotEmpty //Valida que el campo no este vacio
	@NotEmpty(message="El nombre no puede ser vacio") //Valida que el campo no este vacio. Opcional agregarle el mensaje
	private String nombre;
*/
	//Validaciones personalizadas a campos(identificador2, nombre) usando la clase UsuarioValidador
	//private String identificador2;
	
	//Validacion basada de la anotacion o clase IdentificadorRegex
	@IdentificadorRegex //Se le puede agregar un mensaje (message="El nombre no puede ser vacio")  o se puede agregar el mensaje desde el archivo message.properties
	private String identificador2;
	
	private String nombre;
	
	//@NotEmpty //Valida que el campo no este vacio
	//Otra forma de validar - Validando utilizando la clase anotacion Requerido en paquete validation y clase RequeridoValidador
	@Requerido
	private String apellido;

	
	@NotBlank //@NotBlank valida que no exita valores nulos y en blanco   //@NotEmpty //Valida que el campo no este vacio
	@Size(min=3, max=8) //Establecer tamaño minimo y maximo del campo username
	private String username;
	
	@NotEmpty //Valida que el campo no este vacio
	
	private String password;
	
	@NotEmpty //Valida que el campo no este vacio
	@Email(message="Correo con formato incorrecto.")
	private String email;
	
	
	//Validacion de numeros enteros con @Max, @Min y NotNull. y @Min es para datos primitivos int 
	
	@NotNull //@NotNull es para objetos y @NotEmpty y @NotBlank es para String
	@Max(5000) //Maximo 5000
	@Min(5) //minimo numero 5
	private Integer cuenta;

	
	//Validacion de fechas
/*	
	@NotNull
	//@Past //@Past o @PastOrPresent valida que se inserten fechas en pasado.
	@Future //@FutureOrPresent o @Future valida que se inserten fechas en futuro.
	@DateTimeFormat(pattern = "yyyy-MM-dd") //Para mantener el formato "yyyy/MM/dd" aplicado a la fecha luego de enviar. Con pattern = "yyyy/MM/dd" establecemos el formato de la fecha. pero debemos modificar el archivo message.properties y el placeholder del form
	//Al usar nombrar un campo tipo "date" en el formulario se debe usar en la clase del backend spring el formato (pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
*/	
	
	//Formateando fechas con @InitBinder
	
	@NotNull
	//@Past //@Past o @PastOrPresent valida que se inserten fechas en pasado.
	@Future //@FutureOrPresent o @Future valida que se inserten fechas en futuro.
	//@DateTimeFormat(pattern = "yyyy-MM-dd") //Para mantener el formato "yyyy/MM/dd" aplicado a la fecha luego de enviar. Con pattern = "yyyy/MM/dd" establecemos el formato de la fecha. pero debemos modificar el archivo message.properties y el placeholder del form
	//Al usar nombrar un campo tipo "date" en el formulario se debe usar en la clase del backend spring el formato (pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	
	/*
	//Pais de tipo String - Lista select desplegable
	@NotEmpty
	private String pais;
	*/
	
	//LLenando lista select con objetos de la clase Pais 
	//@Valid //Valide en la clase pais se cumpla el NotNull
	@NotNull //Validar el objeto completo - usando PaisPropertyEditor
	private Pais pais;
	
	
	//Roles de tipo List<String>
/*	//Una lista se valida con @NotEmpty
	@NotEmpty
	private List<String> roles;
*/
	
	//Roles de Objeto Role 
	//Una lista se valida con @NotEmpty
	@NotEmpty
	private List<Role> roles;
		
	//Checkbox booleano true o false
	private Boolean habilitar;
	
	@NotEmpty
	private String genero;
	
	
	private String valorSecreto;
	
	
	
	// Getters and Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador2() {
		return identificador2;
	}

	public void setIdentificador2(String identificador2) {
		this.identificador2 = identificador2;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

/*
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
*/
	
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}


	/*
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	*/
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getValorSecreto() {
		return valorSecreto;
	}

	public void setValorSecreto(String valorSecreto) {
		this.valorSecreto = valorSecreto;
	}

	

	
}



