package com.gerson.springboot.error.springbooterror.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.gerson.springboot.error.springbooterror.exceptions.UserNotFoundException;
import com.gerson.springboot.error.springbooterror.models.Error;

@RestControllerAdvice
public class HandlerExceptionController {

    //Forma 1: Basica
/*  @ExceptionHandler({ArithmeticException.class})  //Si solo es una: (ArithmeticException.class)  //Para asociar este metodo con el error ArithmeticException que podemos tener, devuelve: "error 500"
    public ResponseEntity<?> divisionByZero0(Exception ex){ //<Error> es la clase Error o <?> sig que devuelve cualquier tipo.
        return ResponseEntity.internalServerError().body("error 500");//Ahora de devolver un error, Devuelve el ResponseEntity con el error llamado: "error 500"
    }
*/

    //Forma 2 mas customizada
    @ExceptionHandler({ArithmeticException.class})  //Si solo es una: (ArithmeticException.class)  //Para asociar este metodo con el error ArithmeticException que podemos tener.
    public ResponseEntity<Error> divisionByZero(Exception ex){ //<Error> es la clase Error 
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error division por cero!");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        //return ResponseEntity.internalServerError().body(error); //Forma 1: del return
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error); //Forma 1: del return
    }

    //Error 404:
    //Para habilitar este metodo se debe configurar en el archivo application.properties para que spring no maneje el error 404. 
    //Agregar en application.properties:  spring.mvc.throw-exception-if-no-handler-found=true  spring.web.resources.add-mappings=false
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e){

        Error error = new Error();
        error.setDate(new Date());
        error.setError("API Rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        //return ResponseEntity.notFound().build(); //Forma1: Si no se quiere agregar algo en la respuesta
        return ResponseEntity.status(404).body(error); //Forma 2: Opcion 1: Permite agregar la respuesta customizada, se agrega el codigo 401
        //return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error); //Forma 2: Opcion 2: Permite agregar la respuesta customizada
    }


//Exception 404 No  hay necesidad de modificar el application.properties
/*@ExceptionHandler
    public ResponseEntity<String> NotFoundException(Exception e) {
        //Cualquier tipo de exepcion que no se tenga atrapada y/o controlada 
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("error 404-89: EXEPCION-NO-CONTROLADA:\n".concat(e.toString()));
    }
*/

    //Exception de otra forma: 
    @ExceptionHandler(NumberFormatException.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> numberFormatException(Exception ex){

        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "Numero invalido o incorrecto, no tiene forma de digito!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }


    //Manejo  de errores:
    @ExceptionHandler({NullPointerException.class, 
                        HttpMessageNotWritableException.class,
                        UserNotFoundException.class //Nuestra propia exception creada
                    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFoundException(Exception ex){

        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "El usuario o role no existen!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }



    }


