package com.gerson.springboot.app.springbootcrudjpa.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gerson.springboot.app.springbootcrudjpa.entities.User;
import com.gerson.springboot.app.springbootcrudjpa.repositories.UserRepository;


//Cada vez que va a ser Login se usara esta clase JpaUserDetailsService para obtener el username usando esta implementacion
@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repository; // por el repository obtenemos el username de User para comvertir a UserDetails

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        //Metodo para buscar al User por el Username
        Optional<User> userOptional = repository.findByUsername(username); //obtenemos el username que viene del formulario que viene del request
    
        //Si es vacio lanza la exception
        if(userOptional.isEmpty()){ 
            throw new UsernameNotFoundException(String.format("username %s no existe en el sistema!", username)); // %s es una variable que contendra el nombre del usuario o el username definido en la variable username       
        }
        //Si esta presente el user lo obtenemos
        User user = userOptional.orElseThrow(); //orElseThrow obtiene el user en caso de que no esta lanza la exception
        
        //Obtene los roles 
        List<GrantedAuthority> authorities = user.getRoles().stream()//le pasa los roles del tipo GrantedAuthority
        .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList()); //convirtio el .map a una List

            //devolver el UserDetail
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPassword(), 
                user.isEnabled(), 
                true, //Cuenta no expira
                true, //Credenciales no expira
                true, // cuenta no se bloquea
                authorities
            );
    }

}
