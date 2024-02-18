package com.gerson.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="persons") //opcional, Si no se coloca por defecto la tabla en la BD se llamara person, si se agrega entonces nos permite definir que esta clase Person en la BD la tabla es persons
public class Person {

    @Id //indica que este es el id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para Mysq, SQLServer y POSTGRES se utiliza .IDENTIFY para POSTGRES y ORACLE se utiliza .SEQUENCE
    private Long id;
    
    private String name; //SI el nombre del campo de la clase se llama igual que la tabla de la BD no es necesario agregar una anotacion para indicar el nombre de la tabla de la BD que hará relacion con esta clase
    
    private String lastname;

    @Column(name="programming_language") //Con la anotacion Columna name indicamos el nombre de la tabla de la BD cuando este no se llama igual que en la Clase
    private String programmingLanguage;

    //Constructor vacio: Hibernate JPA utilizará el constructor vacio para crear la instancia, poblar los datos de las tablas.
    public Person() {
    }

    //Constructor con parametros: Si tenemos un constructor con parametros estamos obligados a tener un constructor vacio ya que JPA utilizará el constructor vacio para crear la instancia.
    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    //COnstructor para consulta
    public Person(String name, String lastname) {
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


    public String getProgrammingLanguage() {
        return programmingLanguage;
    }


    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    //Metodo toString para imprimir el objeto personas
    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage + "]";
    }


    
}
