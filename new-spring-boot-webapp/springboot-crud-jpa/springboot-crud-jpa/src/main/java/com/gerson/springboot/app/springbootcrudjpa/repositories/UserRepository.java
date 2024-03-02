package com.gerson.springboot.app.springbootcrudjpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.gerson.springboot.app.springbootcrudjpa.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{ //Recibe la clase "User" y el tipo de dato del id de la clase: "Long"

//Metodo basado por nombre del metodo 
    boolean existsByUsername(String username); //Valida si existe el user devuelve true o false

    Optional<User> findByUsername(String username);
}
