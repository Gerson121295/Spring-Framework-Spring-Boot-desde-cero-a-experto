package com.gerson.springboot.app.springbootcrudjpa.services;

import java.util.List;
import java.util.Optional;

import com.gerson.springboot.app.springbootcrudjpa.entities.Product;

public interface ProductService {
    //ProductService es usado en el controller para acceder a toda la funcionalidad de los metodos
    //Firmas de metodos se usan en clase ProductServiceImp(tiene implementado ProductService) el cual tiene inyectado el ProductRepository
    List<Product> findAll();
    
    Optional<Product> findById(Long id);
    
    Product save(Product product);

    Optional<Product> update(Long id, Product product);

    // Optional<Product> delete(Product product);  //Forma 1 
    Optional<Product> delete(Long id);  //Forma 2: Optimizada

    boolean existsBySku(String sku);
}
