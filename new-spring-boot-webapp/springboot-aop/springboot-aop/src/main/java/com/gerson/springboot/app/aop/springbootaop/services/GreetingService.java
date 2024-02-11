package com.gerson.springboot.app.aop.springbootaop.services;

public interface GreetingService {

    //Firma de los metodos
    String sayHello(String person, String phrase);
    String sayHelloError(String person, String phrase); //Este da error por motivos de probar el Aspect - @AfterThrowing se ejecuta cuando hay un error.

}
