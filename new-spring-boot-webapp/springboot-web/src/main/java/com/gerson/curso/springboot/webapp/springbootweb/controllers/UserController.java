package com.gerson.curso.springboot.webapp.springbootweb.controllers;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gerson.curso.springboot.webapp.springbootweb.models.User;


@Controller //Controlador
public class UserController {

    @GetMapping("/details")
    public String details(Model model){  //model para pasar datos del controller a la vista, utiliza el model.addAttribute
        
        /* Otra forma de pasar datos a la vista, Usa el model.put
        public String details(Map<String, Object> model){  
            model.put("title", "Hola mundo spring"); //Pasa el titulo a la vista, "title" es la variable que conecta con la vista y el otro es el texto a pasar
            model.put("name", "Gerson");
            model.put("lastname", "EP");
        */ 
        /* //Forma 2
        model.addAttribute("title", "Hola mundo spring"); //Pasa el titulo a la vista, "title" es la variable que conecta con la vista y el otro es el texto a pasar
        model.addAttribute("name", "Gerson");
        model.addAttribute("lastname", "EP");
        */

        //Forma 3
        User user = new User("Gema", "Esc"); //Se instancia la clase user y se le envia lo datos del usuario
            user.setEmail("Ger@gmail.com");

        model.addAttribute("title", "Hola mundo spring"); //Pasa el titulo a la vista, "title" es la variable que conecta con la vista y el otro es el texto a pasar
        model.addAttribute("user", user);
      
        return "details"; //Retorna a la vista de thymeleaf
    }


    @GetMapping("/list")
    public String list(ModelMap model){
    /*
        List<User> users = Arrays.asList(
            new User("pepa", "Gonzales"), 
            new User("Laura", "Sanchez", "lau@correo.com"), 
            new User("Byron", "Rodas", "byr@correo.com"), 
            new User("Sara","Gomez")
            );
     */
        //model.addAttribute("users", users);
        model.addAttribute("title", "Listado de Usuarios");

        return "list";
    }


    @ModelAttribute("users") //Model atribute permite enviar datos a la vista, users es la variable que conecta con la vista list.html
    public List<User> usersModel(){
        //List<User> users = Arrays.asList(
           return Arrays.asList(
            new User("pepa", "Gonzales"), 
            new User("Laura", "Sanchez", "lau@correo.com"), 
            new User("Byron", "Rodas", "byr@correo.com"), 
            new User("Sara","Gomez")
            );
            //return users;
    }


    


}
