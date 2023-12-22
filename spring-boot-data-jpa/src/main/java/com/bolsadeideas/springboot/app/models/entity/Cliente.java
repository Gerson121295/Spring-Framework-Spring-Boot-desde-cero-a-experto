package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(name="nombre_cliente") //@Column permite especificar como esta
	// nombrado el atributo en la tabla de la BD.
	@NotEmpty //Validacion campo requerido no puede ir vacio. NotEmpty solo se usa con String
	@Size(min=3, max=12) //Tamaño del nombre debe estar entre 4 y 12 caracteres
	private String nombre;
	
	@NotEmpty
	private String apellido;// Cuando en la tabla de la BD es igual al nombre del atributo en la clase no es
							// necesario agregarle @Column
	@NotEmpty
	@Email  //formato email debe ser validado
	private String email;

	@NotNull //valida que la fecha no sea nula. @NotNull se usa con decimal, int, integer, long y Date
	@Column(name = "create_at") // Como se llama este campo en la tabla de la BD
	@Temporal(TemporalType.DATE) // Indica el formato en que se va a guardar la fecha en la tabla de la BD
	@DateTimeFormat(pattern="yyyy-MM-dd") //Establece el formato a recibir la fecha
	private Date createAt;
	
	private String foto;
	
	
	//Relacion de cliente con la tabla factura: Un Cliente puede tener muchas(una lista de) facturas. (muchas es una lista)
	 //Relacion: Un cliente(One) para(To) muchas(Many) facturas . 
	//La clase cliente puede tener muchas facturas. Y muchas factura puede tener un cliente
	 //Con LAZY solo trae la consulta cuando se le llama. 
	//cascade al momento de eliminar o guardar una factura tambien se guardan o eliminan los hijos hay un efecto en cascada. Cuando tenemos relaciones entre tablas de llave foraneas
	//mappedBy="cliente" hace la relacion con la tabla Factura por medio del campo llamado cliente. 
	//Nota: Debido a mappedBy="cliente"  automaticamente se crea el campo de la llave foranea "clienteId" en la tabla facturas para relacionar ambas tablas.
	//orphanRemoval = true Opcional para eliminar items huerfanos que no estan asociados a ninguna factura.
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Factura> facturas; //cliente tendra una lista de facturas
	//En la clase que tenga el @Many(Muchos) creo que se crea el id (cliente_id) por defecto en este caso se crea en clase facturas

	//Metodo - Crea la Fecha actual automaticamente al crear el registro
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	//Constructor
	public Cliente() {
		facturas = new ArrayList<Factura>();//Se inicializa facturas en el constructor
	}
	

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	
	
	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	//Metodo Agregar o guardar factura por factura (guarda 1 factura). Parecido al setFacturas pero el set guarda todas las facturas.
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}
	
	//Imprime el objeto con los valores del atributo para mostrarlo en la vista /factura/ver.html
	@Override
	public String toString() {
		return nombre + " " + apellido;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
