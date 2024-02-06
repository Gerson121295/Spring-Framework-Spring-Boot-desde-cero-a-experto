package com.gerson.springboot.di.app.springbootdi.repositories;

import java.util.List;

import com.gerson.springboot.di.app.springbootdi.models.Product;

public interface ProductRepository {
    //Firma de los metodos que seran implementados en ProductRepositoryImpl
    
    List<Product> findAll(); //Muestra todos los productos
    Product findById(Long id);  //Busca 1 producto por su ID

}
