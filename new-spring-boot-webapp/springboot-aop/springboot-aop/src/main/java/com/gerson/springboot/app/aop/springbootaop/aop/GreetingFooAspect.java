package com.gerson.springboot.app.aop.springbootaop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)//Ordenando los Aspectos con @Order para su ejecucion. //El orden 1 es el primero en entrar y ultimo en salir
@Aspect
@Component
public class GreetingFooAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

     //Metodo para definir el punto de corte para evitar repetir en cada metodo
    @Pointcut("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") //punto de corte
    private void greetingFoodLoggerPointCut(){}

    //@Before("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") // Agregar * en el tipo de dato a retornar "seria cualquier". //Punto de corte cual metodo o package vamos a interceptar. Execution recive("String: tipo de dato que devuelve el metodo, la ruta completa, nombre de la clase y el metodo y sus argumentos") al agregar el GreetingService.*(..) todas las clases que implementen esta interfaz
    @Before("greetingFoodLoggerPointCut()")
    public void loggerBefore(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @Before
        String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
        String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo

        logger.info("Antes Primero:" + method + " invocando con los parametros " + args);
    }

        //After - Especifica que el metodo loggerAfter se ejecutara despues que el metodo especificado en execution
    //@After("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
    @After("greetingFoodLoggerPointCut()")
    public void loggerAfter(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @After
        String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
        String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo

        logger.info("Despues Primero:" + method + " invocando con los parametros " + args);
    }


}
