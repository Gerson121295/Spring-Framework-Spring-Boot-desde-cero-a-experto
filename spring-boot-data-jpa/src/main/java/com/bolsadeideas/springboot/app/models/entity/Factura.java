package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="facturas")
public class Factura implements Serializable {
	
	private static final long serialVersionUID = 1L; //Como se implementa Serializable
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Id se genera automaticamente
	private Long id;
	
	@NotEmpty //El campo descripcion no puede estar vacio. Luego de la validacion en el metodo post controller  se debe agregar la anotacion @Valid
	private String descripcion;
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")//para establecer como se llama la tabla createAt en la BD
	private Date createAt;
	
	 //Relacion muchas(Many) facturas para(To) un cliente(One). 
	//La clase factura puede tener un solo cliente. y Un cliente puede tener muchas facturas
	@ManyToOne(fetch=FetchType.LAZY) //LAZY carga perezosa solo trae la consulta cuando se llama, EAGER trae todo
	private Cliente cliente; //Factura tendra un cliente. //automaticamente se crea un campo cliente_id en la tabla factura que es la llave foranea que relaciona Factura con clientes

	//Relacion de Factura con ItemFactura
	//Una factura contiene muchos elementos items pero itemsFactura no tiene relacion con factura porque no es necesario 
	//Por lo que la relacion es de un solo sentido de factura a itemsFactura(lineaFactura). Una factura tiene muchos itemsFactura(lineaFactura)  
	//una Factura(One) para(To) muchos itemsFactura(Many)
	//cascade=CascadeType.ALL al eliminar una factura tambien elimine todos los elementos hijos sin importar las relaciones de la llaves foraneas
	
	//Se agrega @JoinColumn(name="factura_id") cuando la relacion es de un solo sentido y se define cual campo será la llave foranea
	//que une a las tablas en este caso es el campo "factura_id" definido en el @JoinColumn que se crea automaticamente en la tabla facturas_items(clase ItemFactura).
	//orphanRemoval = true Opcional para eliminar items huerfanos que no estan asociados a ninguna factura.
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	//En la clase que tenga el @Many(Muchos) creo que se crea el id (factura_id) por defecto, en este caso se crea en ItemFactura
	
	//Constructor
	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}
	
	
	//Metodo - Crea la Fecha actual automaticamente al crear el registro
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	

	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@XmlTransient //Sig que cuando se serializa no va a llamar a este metodo. //@XmlTransient omite este atributo en la Serializacion, no lo incluye en el XML
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	//Metodo para agregar 1 solo itemFactura 
	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}
	
	
	//Metodo para calcular el total
	public Double getTotal() {
		Double total = 0.0;
		int size = items.size(); //Calcula el tamaño de elementos de items
		
		for(int i=0; i<size; i++) {
			total += items.get(i).calcularImporte(); //Por cada item calculamos el importe y lo sumamos al total
		}
		return total;
	}
	
	
}
