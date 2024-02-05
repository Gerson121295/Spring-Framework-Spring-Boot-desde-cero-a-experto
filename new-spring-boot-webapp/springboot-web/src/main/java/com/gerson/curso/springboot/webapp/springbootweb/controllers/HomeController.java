package com.gerson.curso.springboot.webapp.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //El redirect redirige generá una nueva peticion a una nueva url existente, se pierden los parametros del request
    //Forward redirige a una url pero se mantiene la peticion existente sin eliminar los parametros del request
    @GetMapping({"", "/", "/home"})  //Al ejeuctar http://localhost:8080/  se redirige a http://localhost:8080/list
    public String home() { 
        //return "redirect:/list"; //No es una vista sera un redirect a la url /list de UserController
        return "forward:/list"; //No es una vista sera un redirect a la url /list de UserController
    }
    

    
    /* //Forward vs Redirect
     La diferencia es que con el Forward se mantiene dentro de la misma peticion http, y no pierdes los parametros que tienes dentro del request
     tampoco cambia la ruta url, ya que no hace un refresh(refresa la pagina), sino que despacha a otra accion del controlador pero sin recargar
     la pagina, mientras que el redirect cambia la ruta url, reinicia el request y refresca el navegador, ademas que todos los parametros del
     request que teniamos antes del redirect se pierden en este nuevo request.
     */
}
