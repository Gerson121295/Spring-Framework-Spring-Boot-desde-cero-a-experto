package com.gerson.springboot.app.springbootcrudjpa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gerson.springboot.app.springbootcrudjpa.entities.Role;
import com.gerson.springboot.app.springbootcrudjpa.entities.User;
import com.gerson.springboot.app.springbootcrudjpa.repositories.RoleRepository;
import com.gerson.springboot.app.springbootcrudjpa.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Lista todos los usuarios
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    // Guarda el user a la BD
    @Override
    @Transactional
    public User save(User user) {
        //Asignando roles y encriptando password con Bcrypt

        //Todo user que se registre debe tener ROLE_USER
        //obtenemos el ROLE_USER y lo guardamos en OptionalRoleUser
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER"); 
        
        //List con los roles para pasarselas al usuario
        List<Role> roles = new ArrayList<>(); 

        //Si esta presente se pasa los roles a la lista roles
        optionalRoleUser.ifPresent(roles::add);// Otra forma: (role -> roles.add(role));  //role es el que trae de la BD y roles es lista la cual se le pasan los roles. 
        
        //SI el rol es admin
        if(user.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        //Pasamos la lista de Roles a los Usuarios
        user.setRoles(roles);


        //Ahora obtenemos el password encriptado que viene de parametro user
        //traemos el password, encriptamos y lo guardamos en la varible passwordEncoded
        String passwordEncoded = passwordEncoder.encode(user.getPassword()); 
        user.setPassword(passwordEncoded); //Pasamos el password al usuario
        //user.setPassword(passwordEncoder.encode(user.getPassword())); //Forma optimizada

        return repository.save(user); //Guardamos el user
    }

    //Valida si existe el user devuelve true o false
    @Transactional(readOnly = true)
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    
}
