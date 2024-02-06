package com.gerson.springboot.di.app.springbootdi.repositories;

import java.util.Arrays;
import java.util.List;

import com.gerson.springboot.di.app.springbootdi.models.Product;

//Repository es la capa de datos.

public class ProductRepository {
    private List<Product> data;

    //Metodo para definir productos en una lista
    public ProductRepository(){
        this.data = Arrays.asList(
            new Product(1L, "Memoria corsair 32", 300L),
            new Product(2L, "CPU Intel Core i9", 850L),
            new Product(3L, "Teclado Razer 60%", 180L),
            new Product(4L, "Motherboard", 490L)
        );
    }

    //Metodo para devolver la data: Devuelve toda la lista de productos
    public List<Product> findAll(){
        return data;
    }

    public Product findById(Long id){
        return data.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(); //.orElseThrow() en caso de que no encuentre el producto lanza una excepcion de que no se encontro, si lo encuentra devuelve el producto.
    }

}




