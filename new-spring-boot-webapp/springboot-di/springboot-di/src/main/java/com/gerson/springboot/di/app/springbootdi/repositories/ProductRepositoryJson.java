package com.gerson.springboot.di.app.springbootdi.repositories;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;

import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerson.springboot.di.app.springbootdi.models.Product;

//Esta es una clase común y corriente esta configurada en clase AppConfig para que sea un repository es para ejemplo
public class ProductRepositoryJson implements ProductRepository{

    private List<Product> list;

    //Forma 1 de importa el archivo.json, no funciona porque esta clase no es un compónente.
    //@Value("classpath:json/product.json")
    //private Resource resource; 




    //Constructor Tiene las opciones para implementacion en esta clase o en la clase AppConfig.app
 /*    public ProductRepositoryJson (Resource resource){ //Si se define la FORMA 1 EN EL ARCHIVO AppConfig ENTONCES SE DEBE MANDAR COMO PARAMETRO
        //Leer los recursos del archivo product.json
        //ClassPathResource resource = new ClassPathResource("json/product.json"); //Forma 2 de importa el archivo.json
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //list = Arrays.asList(objectMapper.readValue(resource.getFile(), Product[].class)); //resource permite obtener el archivo. //Forma 1
            list = Arrays.asList(objectMapper.readValue(resource.getInputStream(), Product[].class)); //resource permite obtener el archivo. //Forma 2
            } catch (IOException e) {
            e.printStackTrace();
        }  
    }
*/

//Forma para implementar la importacion de archivo desde la clase AppConfig se usan 3 metodos
public ProductRepositoryJson (){ 
    //Leer los recursos del archivo product.json
    Resource resource = new ClassPathResource("json/product.json"); //Forma 2 de importa el archivo.json
    readValueJson(resource);
}

public ProductRepositoryJson (Resource resource){ //Si se define la FORMA 1 EN EL ARCHIVO AppConfig ENTONCES SE DEBE MANDAR COMO PARAMETRO
    //Leer los recursos del archivo product.json
    readValueJson(resource);
}

public void readValueJson (Resource resource){ //Si se define la FORMA 1 EN EL ARCHIVO AppConfig ENTONCES SE DEBE MANDAR COMO PARAMETRO
   ObjectMapper objectMapper = new ObjectMapper();
    try {
        //list = Arrays.asList(objectMapper.readValue(resource.getFile(), Product[].class)); //resource permite obtener el archivo. //Forma 1
        list = Arrays.asList(objectMapper.readValue(resource.getInputStream(), Product[].class)); //resource permite obtener el archivo. //Forma 2
        } catch (IOException e) {
        e.printStackTrace();
    }  
}


    @Override
    public List<Product> findAll() {
        return list;
    }

    @Override
    public Product findById(Long id) {
       // return list.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
          
        //Otra forma
          return list.stream().filter(p -> {         
            return p.getId().equals(id);
       }).findFirst().orElseThrow(); //obtiene el primero pero si no lo encuentra devuelve una excepcion
   
    }
    


}
