package com.gerson.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

/* 
    //Ejemplo cuando los atributos y eventos @PrePersist, @PreUpdate se crean en esta clase Person
    // Forma 2 de implementar funcionalidad en esta clase: Se crea la fecha en el campo creat_at cuando se crea el objeto. Se crea la fecha en el campo updated_at cuando se actualiza el objeto.
    // Atributos para el ejemplo  //Metodos para Eventos del ciclo de vidad el objeto entity de persistencia
    @Column(name = "creat_at")
    private LocalDateTime creatAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
*/

    
    //Ejemplo cuando los atributos y eventos @PrePersist, @PreUpdate se crean en una clase externa(Audit)
    //Atributo para integrar la funcionalidad de los eventos de la clase Audit a esta clase Person.
    // Forma 2 de implementar funcionalidad en esta clase: Se crea la fecha en el campo creat_at cuando se crea el objeto. Se crea la fecha en el campo updated_at cuando se actualiza el objeto.
    @Embedded //@Embedded(Incorporando o Incrustada) 
    private Audit audit = new Audit();


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

/* //Ejemplo cuando los atributos y eventos @PrePersist, @PreUpdate se crean en esta clase Person
    // Se movio estos eventos a la clase Audit para luego integrarse a esta clase.
    // Metodos para Eventos del ciclo de vidad el objeto entity de persistencia
    // Metodos Pre se ejecutan antes de realizar la persistencia en la BD.
    // Ejemplo: @PrePersist - Se crea la fecha en el campo creat_at cuando se crea el objeto.
    @PrePersist 
    public void prePersist(){
        System.out.println("Evento del ciclo de vida del entity pre persist");
        this.creatAt = LocalDateTime.now();
    }

    //Ejemplo: @PreUpdate - Se crea la fecha en el campo updated_at cuando se actualiza el objeto.
    @PreUpdate
    public void preUpdate(){
        System.out.println("Evento del ciclo de vida del objeto pre-update");
        this.updatedAt = LocalDateTime.now();
    }
*/


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

    /* 
    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
*/

    //Metodo toString para imprimir el objeto personas
    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
            // + programmingLanguage + ", createAt=" + creatAt + ", updatedAt=" + updatedAt + "]"; //Ejemplo cuando los atributos y eventos @PrePersist, @PreUpdate se crean en esta clase Person
         + programmingLanguage + ", createAt=" + audit.getCreatAt() + ", updatedAt=" + audit.getUpdatedAt() + "]"; //Ejemplo cuando los atributos y eventos @PrePersist, @PreUpdate se crean en una clase externa(Audit)
    }
    
}
