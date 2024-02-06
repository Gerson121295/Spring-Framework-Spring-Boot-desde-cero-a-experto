package com.gerson.springboot.di.app.springbootdi.models;

public class Product implements Cloneable{
    private Long id;
    private String name;
    private Long price;

    public Product(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    //Costructor vacio para pasar los parametros mediante el set
    public Product() {
    }

    //Getters and setters
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
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }

    // para la  interfaz implements Cloneable
    @Override
    public Object clone()  {    
        try {
            return super.clone(); //para clonar
        } catch (CloneNotSupportedException e) { //si ocurre un error lo clona de la siguiente manera
            //e.printStackTrace();
            return new Product(id, name, price); //otra forma:  return new Product(this.getId, this.getName, this.getPrice);
        }
       
    }



    
}
