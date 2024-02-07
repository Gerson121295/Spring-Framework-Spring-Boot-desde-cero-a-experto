package com.gerson.springboot.di.factura.springbootdifactura.models;

//Un producto esta asociado a muchos item
public class Product {

    private String name;
    private Integer price;

    //Constructor para crear Productos
     public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    //Constructor Vacio en caso de crear un producto vacio
    public Product() {
    }

    //Getters and Setters
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
    
}
