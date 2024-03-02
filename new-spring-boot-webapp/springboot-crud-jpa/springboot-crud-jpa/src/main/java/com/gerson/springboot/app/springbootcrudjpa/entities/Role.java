package com.gerson.springboot.app.springbootcrudjpa.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
public class Role {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //el campo name de Role será unique(unico)
    private String name;


    //Relacion ManyToMany - Bidireccional entre Role y User
    //Many(muchos roles) To(tienen) Many(muchos users)
    //mappedBy = "roles" se mapea(relaciona) por el atributo roles que definida en User, mappedBy define que roles es la clase Padre y User la clase hija dueña de la relacio la cual tiene @JoinColumn 
   //JsonIgnoreProperties - Ignora ciertos atributos del objeto User que causan el error ciclico de crear muchas veces los usuarios. De la clase User ignoramos la lista "roles" 
    @JsonIgnoreProperties({"roles", "handler", "hibernateLazyInitializer"}) //Ignora roles de User e ignora contenido basura que puede generase como: "handler", "hibernateLazyInitializer"
    @ManyToMany(mappedBy = "roles")
     private List<User> users; // Los roles tendran una lista de users



    //Constructores
    public Role() {
        this.users = new ArrayList<>(); // se inicializa la list users
    }

    public Role(String name) {
        this(); //Debido a que en el constructor sin parametros tiene inicializada un arrayList
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    //Metodo HashCode and Equals para eliminar algun registro
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

   
    
    
    
}
