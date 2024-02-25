package com.gerson.curso.springboot.jpa.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    //@Column(name = "descriptions") //En caso de que se quisiera que el nombre del campo description de esta clase Invoice se llame descriptions en la tabla invocices de la BD
    private String description; //Este es el nombre que tendra en la tabla invocices de la BD
    private Long total;

    //Cada factura esta asociada a un cliente, 1 factura 1 cliente, pero 1 cliente puede tener muchas facturas.
    //Muchas facturas hacia un cliente. Many(clase invoices)To(hacia)One(client)
    @ManyToOne //Invoices tendra la FK hacia clientes. por defecto el nombre de la FK sera client_id este campo se crea solo en la tabla invoices
    //@JoinColumn(name = "id_cliente_temp") //JoinColumn sirve para especificar el nombre de la LLave foranea FK, para que no quede por defecto: client_id, por lo tanto se creará este campo id_cliente_temp en la tabla invoices
    @JoinColumn(name = "client_id") //Facturas es el dueño de la relacion, Tener en cuenta que donde va el @JoinColumn es el dueño de la relacion, donde va el FK.
    private Client client;

    //Constructores
    public Invoice() {
    }

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    
    //Getters and Setters
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //Ene el metodo toString Cuando hay una relacion Bidireccional no agregar el cliente en toString Invoice y no agregar invoice en toString de la clase Cliente, evitar agregarlo 2 veces, solo una. 
    @Override
    public String toString() {
        return "{Id=" + Id + 
        ", description=" + description + 
        ", total=" + total + 
        "}";
    }

    //HashCode and equals para eliminar por id. HashCode se aplica a los set el equals a los List.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        if (Id == null) {
            if (other.Id != null)
                return false;
        } else if (!Id.equals(other.Id))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        return true;
    }
    
    

}
