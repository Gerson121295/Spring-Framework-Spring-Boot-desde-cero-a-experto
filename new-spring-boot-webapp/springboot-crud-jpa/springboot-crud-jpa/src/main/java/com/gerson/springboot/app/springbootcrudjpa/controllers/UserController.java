package com.gerson.springboot.app.springbootcrudjpa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.springboot.app.springbootcrudjpa.entities.User;
import com.gerson.springboot.app.springbootcrudjpa.services.UserService;

import jakarta.validation.Valid;

//CORS - Intercambio de origen cruzado para conexion entre el backend y el frontend, 
//@CrossOrigin(origins = "http://localhost:4200")  // (originPatterns = "*")  todas la rutas.   //Por defecto si no se agregan se definen todas las cabeceras
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list(){
        return service.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") //forma 2 de agregar seguridad, solo admin podrean crear usuarios, la forma 1 se define en SpringSecurityCOnfig: .requestMatchers(HttpMethod.POST,"/api/users").hasRole("ADMIN")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        //Este es el orden en el cual debe ir cuando se maneja validaciones: @Valid @RequestBody Product product, BindingResult result
        if(result.hasFieldErrors()){  //result Valida si tiene error en las validaciones
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    //Metodo Endpoint Register - Para cualquier usuario al crear el usuario por defecto solo tendra el ROLE_USER
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
        //forma 1 de implentarlo
/*    if(result.hasFieldErrors()){  //result Valida si tiene error en las validaciones
            return validation(result);
        }
        user.setAdmin(false); //Asigna el atributo admin como false por lo que será ROLE_USER
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
*/ 
        //forma 2: Optimizada de implentarlo
        user.setAdmin(false); //Asigna el atributo admin como false por lo que será ROLE_USER
        return create(user, result); //llama al metodo create y le pasa el user y result para que valide y cree el usuario, solo llevará el campo Admin cono false.
    }

   // Prueba Endpoint register: POST: http://localhost:8080/api/users/register  {"username":"pepa", "password":"12345"}

   
/*
Crear usuario por defecto ROLE_USER : http://localhost:8080/api/users   { "username":"andres", "password":"12345" }
Crear usuario como ROLE_ADMIN : http://localhost:8080/api/users   { "username":"andres", "password":"12345","admin":true }
*/


    //Metodo de Validacion si existiera error en las validaciones de ingreso de datos del producto
//private ResponseEntity<Map> validation(BindingResult result) {   //Forma 1
private ResponseEntity<?> validation(BindingResult result) {   //Forma 2: Signo generico: <?> 
    Map<String, String> errors = new HashMap<>(); //Variable: errors guarda los errores que se encuentren
    //foreach recorre los campos si encuentra error muestra el mensaje
    result.getFieldErrors().forEach(err -> {
     errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());     
    }); 
    return ResponseEntity.badRequest().body(errors);
}


}
