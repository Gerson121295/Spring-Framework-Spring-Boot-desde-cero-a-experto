package com.gerson.springboot.error.springbooterror;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gerson.springboot.error.springbooterror.models.domain.User;

@Configuration

public class AppConfig {

        //Creamos los datos de la lista. Emular una BD para luego importarla en UserServiceImpl
    @Bean //Por defecto el nombre del componete será users
    public List<User> users(){ //por defecto el public se puede omitir
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "pepe", "Gonzales"));
        users.add(new User(2L, "Andres", "Mena"));
        users.add(new User(3L, "Maria", "Perez"));
        users.add(new User(4L, "Josefa", "Ramirez"));
        users.add(new User(5L, "Ale", "Gutierrez"));
        return users;
    }
}

