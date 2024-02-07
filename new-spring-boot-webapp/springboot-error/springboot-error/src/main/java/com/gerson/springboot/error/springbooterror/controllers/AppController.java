package com.gerson.springboot.error.springbooterror.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.springboot.error.springbooterror.exceptions.UserNotFoundException;
import com.gerson.springboot.error.springbooterror.models.domain.User;
import com.gerson.springboot.error.springbooterror.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService service;

    @GetMapping
    public String index() {
        //int value = 100/0; //division por 0 da error lanza una exception: ArithmeticException
        int value = Integer.parseInt("10x"); //10x da error o lanza una exception: NumberFormatException  //Definir un int y escribir un String 10x y tratar de convetir a int lanza una exption.
        System.out.println(value);
        return "ok 200";
    }

    //Forma para manejo de excepcion 1
/*  @GetMapping("/show/{id}")
    public User show(@PathVariable(name = "id") Long id){
        //return service.findById(id);
        User user = service.findById(id); 
    
        //Manejar la excepcion en el controller es lo mas recomndado para no ensuciar el service
        if(user == null){
            throw new UserNotFoundException("Error el usuario no existe!");
        }

        System.out.println(user.getLastname());
        return user;
    }
 */
    
     //Forma para manejo de excepcion 2 usando Optional (mas elegante)
 @GetMapping("/show/{id}")
    public User show(@PathVariable(name = "id") Long id){
        User user = service.findById(id).orElseThrow(() -> new UserNotFoundException("Error el usuario no existe!")); 

        System.out.println(user.getLastname());
        return user;
    }


     //Forma para manejo de excepcion 2 opcion 2 usando Optional //Solo que no muestra el error o mensaje solo retorna el 404 not found
/*     @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable(name = "id") Long id){
        Optional<User> optionalUser = service.findById(id);

        if(optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        //System.out.println(user.getLastname());
        return ResponseEntity.ok(optionalUser.orElseThrow());
    }
*/



}
