package com.gerson.springboot.di.app.springbootdi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.springboot.di.app.springbootdi.models.Product;
import com.gerson.springboot.di.app.springbootdi.services.ProductServiceImpl;


@RestController
@RequestMapping("/api")
public class SomeController {

    
    //private ProductServiceImpl service = new ProductServiceImpl(); //Se crea la instancia e Inyecta para acceder a los metodos que obtiene los datos

    @Autowired  //No tenemos que crear la instancia ni llamarla Spring se encargará de crear y llamarla.
    private ProductServiceImpl service;  //Se inyecta

    @GetMapping
    public List<Product> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product show(@PathVariable Long id) {
        return service.findById(id);
    }
    
}
