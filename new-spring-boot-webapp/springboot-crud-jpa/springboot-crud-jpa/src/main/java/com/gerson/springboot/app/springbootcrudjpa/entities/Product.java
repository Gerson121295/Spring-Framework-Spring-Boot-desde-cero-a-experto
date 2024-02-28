package com.gerson.springboot.app.springbootcrudjpa.entities;

import com.gerson.springboot.app.springbootcrudjpa.validation.IsExistsDb;
import com.gerson.springboot.app.springbootcrudjpa.validation.IsRequired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @IsRequired
    @IsExistsDb // Validacion personalizada usando anotacion check DB
    private String sku;

    //Validaciones: Todo lo que sea String usa NotEmpty o NotBlank, para objeto usar NotNull(Clase, fecha(Date), DateTime, Integer)
    //variables definidas en el message estan en el archivo messages.properties
    //@NotEmpty(message = "{NotEmpty.product.name}") //para todo tipo de dato String.
    @Size(min=3, max = 20) //Para caracteres: no tener menos de 3 o maximo 20 caracteres
    @IsRequired(message = "{IsRequired.product.name}") //Validando todo dato tipo String. usando Clase personalizada IsRequired
    private String name;

    //@Pattern: para definir expresiones regulares
    //@Min(500) //Valida que sea un entero y tenga como valor minimo 500
    @Min(value = 500, message = "{Min.product.price}")
    @NotNull(message = "{NotNull.product.price}") //que no sea nulo
    private Integer price;

    //@NotBlank  // Valida que no este vacio y que no tenga espacios en blanco.
    //@NotEmpty(message = "{NotBlank.product.description}")
    @IsRequired //Validacion usando una clase personalizada
    private String description;
    
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

    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    

}


