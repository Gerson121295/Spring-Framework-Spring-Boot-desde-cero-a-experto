package com.gerson.springboot.error.springbooterror.models.domain;

public class User {

    private Long id;
    private String name;
    private String lastname;

    private Role role;

    //Constructor
    public User() {
    }

    public User(Long id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }
   
    //Getters and Setters
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
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //Obtiene el objeto del Rol
    public Role getRole() {
        return role;
    }

    ///Obtener el nombre del Rol
/*    public String getRoleName() {
        return role.getName();
    }
*/ 

    public void setRole(Role role) {
        this.role = role;
    }
    
}
