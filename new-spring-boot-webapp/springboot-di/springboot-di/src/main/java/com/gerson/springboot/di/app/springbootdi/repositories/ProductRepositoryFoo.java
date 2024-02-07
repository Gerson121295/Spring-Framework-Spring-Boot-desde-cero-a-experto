package com.gerson.springboot.di.app.springbootdi.repositories;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.gerson.springboot.di.app.springbootdi.models.Product;

//Clase de ejemplo para implementar la anotacion @Primary en  RepositoryFoo

//@Primary //Al anotar primary especifica a spring que este será el primer Repository o el principal dentro de otros, Solo puede haber una anotacion @Primary
//@Repository
@Repository("productFoo") //Se le agrega nombre al Repositorio para ser llamado en la anotacion @Qualifier de la clase ProductServiceImpl en caso de no agregar nombre agregar el nombre de la clase pero iniciando en minuscula.
public class ProductRepositoryFoo implements ProductRepository{
    
    @Override
    public List<Product> findAll() {
        
        return Collections.singletonList(new Product(1L, "Monitor Asus 27", 600L));
    }

    @Override
    public Product findById(Long id) {
        return new Product(id, "Monitor Asus 27", 600L);
    }

}
