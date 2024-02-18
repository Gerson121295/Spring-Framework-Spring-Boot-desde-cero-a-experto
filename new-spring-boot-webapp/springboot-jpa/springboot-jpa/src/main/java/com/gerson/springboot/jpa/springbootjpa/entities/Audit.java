package com.gerson.springboot.jpa.springbootjpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

//Ejemplo de Anotaciones @Embedded y @Embeddable

@Embeddable //@Embeddable(incrustable o incorporable) en otra clase usando la anotacion: @Embedded(Incorporando o Incrustada)
public class Audit {

    //Implementar funcionalidad: Se crea la fecha en el campo creat_at cuando se crea el objeto. Se crea la fecha en el campo updated_at cuando se actualiza el objeto.
    // Atributos para el ejemplo  //Metodos para Eventos del ciclo de vidad el objeto entity de persistencia
    @Column(name = "creat_at")
    private LocalDateTime creatAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

       //Metodos para Eventos del ciclo de vidad el objeto entity de persistencia
    //Metodos Pre se ejecutan antes de realizar la persistencia en la BD.
    //Ejemplo: @PrePersist - Se crea la fecha en el campo creat_at cuando se crea el objeto.
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


    //Getters and Setters
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

}
