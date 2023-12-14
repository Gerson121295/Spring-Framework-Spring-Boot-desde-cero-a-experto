package com.bolsadeideas.springboot.form.app.models.domain;

//Para pasar los roles en forma de objeto

public class Role {

	private Integer id;
	private String nombre;
	private String role;

	public Role() {

	}

	public Role(Integer id, String nombre, String role) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	
	//Se implementa el metodo Equals para que por defecto aparezca seleccionado el rol usuario en la lista
	
	@Override
	public boolean equals(Object obj) {
		//Valida si es igual los obj que se pasan a la instancia, si es igual retorna true;
		if(this == obj) {
			return true;
		}
		
		//Si no es una instancia Role el objeto retorna false
		if(!(obj instanceof Role)) {
			return false;
		}
		
		//Si es una instancia Role el objeto entonces retorna el id.
		Role role = (Role) obj; //Se hace el casteo del obj a Role
		
		//Preguntar si el id del que se esta comparando sea diferente de null. !=null y compara el id del objeto con el rol
		return this.id != null && this.id.equals(role.getId());
	}
	
	

}




