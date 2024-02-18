package com.gerson.springboot.jpa.springbootjpa.dto;

//Ejemplo para JPQL instanciacion dinamica de clase DTO personalizada
//Clase DTO para clase Person. La clase DTO es una clase personalizada de la clase Entity Person
public class PersonDto {

    private String name; 
    private String lastname;

    //Constructores
    public PersonDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    //Getters and Setters
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

    @Override
    public String toString() {
        return "[name=" + name + ", lastname=" + lastname + "]";
    }


    
}
