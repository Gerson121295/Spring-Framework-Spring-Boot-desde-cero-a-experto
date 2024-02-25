package com.gerson.curso.springboot.jpa.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="clients_details")
public class ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean premium;
    private Integer points;

  /* //Ejemplo 1 - de relacion Unidireccional OneToOne entre ClientDetails y Clients
    //Un One(un clientDetails) To(tiene) One(un cliente)
    //ClientDetails es dueño de la relacion, aqui se encuentra la Fk llamada client_id este campo se crea en esta tabla clients_details.
    @OneToOne
    private Client client;
*/  

    // Ejemplo 1: relacion Bidireccional OneToOne entre ClientDetails y Client
    //ClientDetails es dueño de la relacion, aqui se encuentra la Fk llamada client_id por defecto(pero se cambio de nombre con JoinColumn ahora es "id_cliente") este campo se crea en esta tabla clients_details.
    //ClientDetails es la dueña de la relacion pero la padre o la principal es Client y ahi se agrega: el cascade (cuando se crea o elimina un cliente tambien se crea el ClientDetail) el orphaneRemove(para eliminar las relaciones entre client y ClientDetails)
    @OneToOne
    @JoinColumn(name="id_client")
    private Client client;


    public ClientDetails() {
    }

    public ClientDetails(boolean premium, Integer points) {
        this.premium = premium;
        this.points = points;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public String toString() {
        return "{id=" + id + ", premium=" + premium + ", points=" + points + "}";
    }

    
}
