package com.gerson.springboot.di.factura.springbootdifactura.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
//@JsonIgnoreProperties({"targetSource", "advisors"})//Se agrego xq al agregar SessionScope surgieron error. //targetSource: Error en consola: No serializer found for class. advisors es otro error que se ignoró.
public class Client {

    @Value("${client.name}") //Accede a los valores definidos en el archivo data.properties y se lo asigna a la variable name.
    private String name;

    @Value("${client.lastname}")
    private String lastname;

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

    
}
