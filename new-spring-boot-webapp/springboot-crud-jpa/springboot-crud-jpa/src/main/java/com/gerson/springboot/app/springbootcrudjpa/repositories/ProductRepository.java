package com.gerson.springboot.app.springbootcrudjpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.gerson.springboot.app.springbootcrudjpa.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{ //Se envia la clase y su tipo de dato de Id

    //Validacion personalizada usando anotacion check DB
    //Metodo basado en nombre - para consultar si exite el campo SKU(codigo) de un product en la BD
    boolean existsBySku(String sku); //si lo encuentra retorna true sino false
}
