package com.gerson.curso.springboot.webapp.springbootweb.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.curso.springboot.webapp.springbootweb.models.User;
import com.gerson.curso.springboot.webapp.springbootweb.models.dto.UserDto;


@RestController //Para API Rest recomendado
//@Controller //pero tambien se puede usar @Controller pero hay que agregarle al metodo la anotacion @ResponseBody
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/details-map") 
    //@RequestMapping(path="/details", method = RequestMethod.GET) //Otra forma de establecer la ruta  //@RequestMapping(value = "/details", method = RequestMethod.GET)
    //@ResponseBody  //Si se usa @Controller en la cabecera de la clase del controller se debe agregar @ResponseBody
        public Map<String, Object> detailsMap(){  
            //public User details(){   //Para retornar solo los datos del usuario el return se agrega user
            User user = new User("Gema", "Esc");
            Map<String, Object> body = new HashMap<>();

            body.put("title", "Hola mundo spring"); //Pasa el titulo a la vista, "title" es la variable que conecta con la vista y el otro es el texto a pasar
            // Se especifican los nombres
            //body.put("name", "Gerson");
            //body.put("lastname", "EP");

            //Los datos viene de la instancia User
            body.put("user", user); 

        return body; //user - al retornar user solo devolveremos los datos del usario en formato JSON
    }

    //Usando DTO POJO
    @GetMapping("/details") 
    //@RequestMapping(path="/details", method = RequestMethod.GET) //Otra forma de establecer la ruta  //@RequestMapping(value = "/details", method = RequestMethod.GET)
    //@ResponseBody  //Si se usa @Controller en la cabecera de la clase del controller se debe agregar @ResponseBody
        public UserDto details(){  
            UserDto userDto = new UserDto();
            User user = new User("Gerson", "Ep");

            //Forma 1 de obtener los datos especificando en UserDto el objeto User
            userDto.setUser(user);
            userDto.setTitle("Hola Mundo Springboot");

            //Forma 2 de obtener los datos especificando en UserDto los atributo de la clase User
            /*userDto.setTitle("Hola Mundo Springboot");
            //userDto.setName(user.getName());
           // userDto.setLastname(user.getLastname());
            */

           //Forma 3 obtiene el nombre completo de user
           /* userDto.setTitle("Hola Mundo Springboot");
              userDto.setFullname(user.getName().concat(" ").concat(user.getLastname())); //Ejemplo obtener el nombe completo 
            */

        return userDto; 
    }

    @GetMapping("/list")
    public List<User> list(){
        //Instancias de usuarios
        User user = new User("Gerson", "Ep");
        User user2 = new User("Andres", "Guzman");
        User user3 = new User("Rocio", "Esc");

        // Forma 1 de Creacion de Lista users y se agrega los usuarios creados al Arrays
    /*     List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
    */
        // Forma Optimizada 2 de Creacion de Lista users y se agrega los usuarios creados al Arrays
        List<User> users = Arrays.asList(user, user2, user3);

        return users;
    }



}



