package com.gerson.curso.springboot.webapp.springbootweb.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.curso.springboot.webapp.springbootweb.models.User;
import com.gerson.curso.springboot.webapp.springbootweb.models.dto.ParamDto;

//Parametro en la ruta: PathVariable path variable puede cambiar
@RestController
@RequestMapping("/api/var")
public class PathVariableController {

    //Configurando variables definidas en el application.properties
    
    //Inyectar estos valores al controlador
    @Value("${config.username}") //Especificado en el archivo application.properties
    private String username;

    //@Value("${config.message}")  //Especificado en el archivo application.properties
    //private String message;

    //Forma automatica 
    @Value("${config.listOfValues}")
    //private String[] listOfValues;  //Arreglo
    private List<String> listOfValues;  //Lista

    @Value("${config.code}")
    private String code;

    //Forma manual - Gato significa lenguaje de expresion
    //@Value("#{ '${config.listOfValues}'.split(',')}") //split permite cortar donde exista una "," y lo convierte en un arreglo. Lo mismo que la de arriba con List pero esta es manual
    @Value("#{ '${config.listOfValues}'.toUpperCase().split(',')}") 
    private List<String> valueList;

    //usando un String
    @Value("#{ '${config.listOfValues}'.toUpperCase()}")
    private String valueString;

    //Objeto JSON desde values.properties
    @Value("#{${config.valuesMap}}")
    private Map<String, Object> valuesMap;

    //Obtenemos el producto del JSON
    @Value("#{${config.valuesMap}.product}")
    private String product;

    //Obtenemos el precio del JSON
    @Value("#{${config.valuesMap}.price}")
    private Long price;


    //Objeto Environment de Spring para leer configuraciones
    @Autowired
    private Environment environment;




    @GetMapping("/baz/{message}")  //Prueba: http://localhost:8080/api/var/baz/hola
    public ParamDto baz(@PathVariable String message){

        ParamDto param = new ParamDto();
        param.setMessage(message);
        return param;
    }

    @GetMapping("/mix/{product}/{id}")  //http://localhost:8080/api/var/mix/teclado/123
    public Map<String, Object> mixPathVar(@PathVariable String product, @PathVariable Long id){

        Map<String, Object> json = new HashMap<>();
        
        json.put("product", product);
        json.put("id", id);

        return json;
    }


    @PostMapping("/create")  //POST: http://localhost:8080/api/var/create   JSON a enviar:{ "name":"Gerson", "lastname":"Doe", "email":"ger@gmail.com" }
    public User create(@RequestBody User user){

        user.setName(user.getName().toUpperCase()); //cambia el nombre a mayuscula
        return user;
        
    }


    //Metodo usando configuraciones del archivo application.properties
    @GetMapping("/values")   //GET: http://localhost:8080/api/var/values
    public Map<String, Object> values(@Value("${config.message}") String message){//Comentamos el atributo message para dar un ejemplo de como inyectar
        
        Long code2 = environment.getProperty("config.code", Long.class);

        Map<String, Object> json = new HashMap<>();
        json.put("username", username);
        json.put("code", code);
        json.put("message", message);
        json.put("message2", environment.getProperty("config.message")); //Usando environment para leer configuraciones de Spring
        //json.put("code2", environment.getProperty("config.code"));
        //json.put("code2", Integer.valueOf(environment.getProperty("config.code"))); //convierte el code 2 de String a integer
        json.put("code2", environment.getProperty("config.code", Long.class)); //Convierte el code2 a long gracias a la propiedad de environment de devolver el dato en un tipo de dato
        json.put("code2", code2); //Convierte el code2 a long gracias a la propiedad de environment de devolver el dato en un tipo de dato
        json.put("listOfValues", listOfValues);
        json.put("valueList", valueList);
        json.put("valueString", valueString);
        json.put("valuesMap", valuesMap);
        json.put("product", product);
        json.put("price", price);

        return json;
    }



}

