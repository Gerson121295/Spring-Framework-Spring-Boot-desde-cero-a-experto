package com.gerson.springboot.app.springbootcrudjpa.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.gerson.springboot.app.springbootcrudjpa.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{ //Recibe la clase "Role" y el tipo de dato del id de la clase: "Long"

//Consulta basado en el nombre del role: findBy Name(Name es nombre del atributo name de la tabla Role con inicial mayuscula)
Optional<Role> findByName(String name);


}
