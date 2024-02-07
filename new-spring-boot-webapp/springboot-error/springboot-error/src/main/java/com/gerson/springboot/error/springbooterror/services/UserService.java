package com.gerson.springboot.error.springbooterror.services;

import java.util.List;
import java.util.Optional;

import com.gerson.springboot.error.springbooterror.models.domain.User;

public interface UserService {

    //Firma de los metodos
    List<User> findAll();
    //User findById(Long id); //Forma para manejo de excepcion 1
    Optional<User> findById(Long id); //Forma para manejo de excepcion 2
}
