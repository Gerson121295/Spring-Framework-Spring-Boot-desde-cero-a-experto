package com.gerson.springboot.app.springbootcrudjpa.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gerson.springboot.app.springbootcrudjpa.validation.ExistsByUsername;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExistsByUsername //valida si existe el usuario en la BD. Validacion personalizada creada en la clase ExistsByUsername
    @NotBlank //EL name no puede ser vacio ni estar en blanco
    @Size(min=4, max = 12) //username debe tener un minimo de 4 a 12 caracteres
    @Column(unique = true) //el campo username de User será unique(unico)
    private String username;

    @NotBlank //EL password no puede ser vacio ni estar en blanco
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Forma 1: para definir que el atributo password solo es de escritura no se mostrara el campo password en el Json cuando se listen los users
    //@JsonIgnore //Forma 2: Ignora el campo password del JSON, no se podra escribir el password, no se mostrara el campo password en el Json cuando se listen los user
    private String password;

    //Este codigo tambien funciona para Relacion ManyToMany - Bidireccional entre Role, solo se agrego @JsonIgnoreProperties para evitar error ciclico de que sea muchos usuarios.
    //Relacion ManyToMany - Unidireccional: De y entre User(users) a(y) Role(roles) : Un usuario puede tener muchos roles y roles pueden tener muchos usuarios
    //Many(muchos users) To(tienen) Many(muchos roles). 
    //JsonIgnoreProperties, Ignora ciertos atributos del objeto ROLE que causan el error ciclico de crear muchas  veces a los users. De la clase Role ignoramos la lista "users"
    @JsonIgnoreProperties({"users", "handler", "hibernateLazyInitializer"}) //Ignora users de ROLE e ignora contenido basura que puede generase como: "handler", "hibernateLazyInitializer"
    @ManyToMany
    @JoinTable(  //Para definir la tabla intermedia si no se agrega se genera una automaticamente por spring
        name = "users_roles",
        joinColumns = @JoinColumn(name="user_id"), //definimos cual es la FK principal
        inverseJoinColumns = @JoinColumn(name="role_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})} //Restriccion estos campos serán unicos(no se repetirán) en la tabla users_roles
    )
    private List<Role> roles; //users tendra una lista de roles

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Para definir que el atributo enabled solo sea de escritura no se mostrara el campo enabled en el Json cuando se listen los users
    //Para definir por defecto que enable sea true = 1
    private boolean enabled; //Forma 1: private boolean enabled = true;
    
    //Forma 2: Para definir por defecto que enable sea true = 1
    @PrePersist
    public void PrePersist(){
        enabled = true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Para definir que el atributo admin solo sea de escritura no se mostrara el campo admin en el Json cuando se listen los users
    @Transient //Para indicarle a JPA e hibernate que admin no es un campo que este mapeado a la tabla users de la BD es solo una bandera
    private boolean admin; 


    //Constructors
    public User() {
        roles = new ArrayList<>(); // se inicializa la list roles
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    //Metodo HashCode and Equals para eliminar algun registro

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    
}
