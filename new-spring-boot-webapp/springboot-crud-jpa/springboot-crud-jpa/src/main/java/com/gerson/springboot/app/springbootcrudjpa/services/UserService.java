package com.gerson.springboot.app.springbootcrudjpa.services;

import java.util.List;

import com.gerson.springboot.app.springbootcrudjpa.entities.User;

public interface UserService {

    //Esta Interface UserService será inyectada en el Controller y esta Interface UserService --> es implementada en UserServiceImpl y en UserServiceImpl esta se inyecta el UserRepository que contiene acceso a la BD

    //Firma de los metodos
    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username); //Valida si existe el user devuelve true o false

}
