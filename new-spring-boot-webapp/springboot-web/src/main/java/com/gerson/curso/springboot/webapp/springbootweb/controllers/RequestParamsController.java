package com.gerson.curso.springboot.webapp.springbootweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.curso.springboot.webapp.springbootweb.models.dto.ParamDto;
import com.gerson.curso.springboot.webapp.springbootweb.models.dto.ParamMixDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/params")
public class RequestParamsController {

    //Anotacion  @RequestParam
    @GetMapping("/foo")  //Prueba: localhost:8080/api/params/foo?message=hola mundo
    public ParamDto foo(@RequestParam(required = false, defaultValue = "hola") String message){ 
        //agregar required false significa que el parametro no es obligatorio enviarlo, 
        //defaultValue permite especificar un valor por defecto si no se envia un texto en el message
        // name="mensaje" En el @RequestParam se le puede enviar el nombre del parametro, en caso de no enviarlo toma por defecto el dado String message

        ParamDto param = new ParamDto();
        param.setMessage(message);
        //param.setMessage(message != null ? message: "hola"); //si no se envia un texto en la variable mensaje, por defecto se enviara hola
        return param;
    }

    @GetMapping("/bar")  //Prueba: localhost:8080/api/params/bar?text="hola"&code=5
    public ParamMixDto bar(@RequestParam String text, @RequestParam Integer code){
        
        ParamMixDto params = new ParamMixDto();
        params.setMessage(text);
        params.setCode(code);

        return params;
    }

    //Objeto Request desde HttpServletRequest - De forma nativa
    @GetMapping("/request")  //Prueba: localhost:8080/api/params/request?code=17&message="cripto"
    public ParamMixDto request(HttpServletRequest request){
        
        //Ejemplo validar code
        Integer code = 0; //Si no se envia el valor del codigo o se envia texto, Por defecto el valor de code es 0
        try{
            code = Integer.parseInt(request.getParameter("code"));
        } catch(NumberFormatException e){

        }

        ParamMixDto params = new ParamMixDto(); //Instancia la clase ParamMixDto
        params.setCode(code);
        //params.setCode(Integer.parseInt(request.getParameter("code"))); //ejemplo sin validar(declar variable code y validar con try catch)
        params.setMessage(request.getParameter("message"));
        return params;
    }





    
}
