package com.gerson.curso.springboot.webapp.springbootweb.models.dto;

import com.gerson.curso.springboot.webapp.springbootweb.models.User;

//Un objeto DTO es un POJO "Plain Old Java Object"
public class UserDto {

    //Forma obtiene los datos del User por un objeto User
    private String title;
    private User user;

    //Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


//Otra forma usando los atributos de la clase User
/* 
    private String name;
    private String lastname;

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
*/

    //Ejemplo obtener le nombre completo 
 /*    private String fullname;

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
*/  

}
