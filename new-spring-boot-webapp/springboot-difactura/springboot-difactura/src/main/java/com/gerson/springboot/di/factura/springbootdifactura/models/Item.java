package com.gerson.springboot.di.factura.springbootdifactura.models;

//Un item tiene un producto


public class Item {

    private Product product;
    private Integer quantity;

    //Constructor Vacio en caso de crear un item vacio
     public Item() {
    }

    //Constructor para crear Items
    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    //Getters and Setters
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    //Total 
    public int getImporte(){
        return quantity * product.getPrice();
    }

}
