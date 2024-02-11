package com.gerson.springboot.app.aop.springbootaop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//Componente Aspect: Manejo de aspecto interceptar los metodos con todo el abanico de posibilidades que se tiene.

@Order(2)//Ordenando los Aspectos con @Order para su ejecucion
@Aspect
@Component
public class GreetingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //Before especifica que el metodo loggerBefore se ejecutara antes que el metodo especificado en execution
    //@Before("execution(* *.*(..))") //se aplica a todo //muy generico no se recomienda
    //@Before("execution(* com.gerson.springboot.app.aop.springbootaop..*.*(..))") 
    //@Before("execution(* com.gerson.springboot.app.aop.springbootaop.services.*.*(..))") // Agregar * en el tipo de dato a retornar "seria cualquier". //Punto de corte cual metodo o package vamos a interceptar. Execution recive("String: tipo de dato que devuelve el metodo, la ruta completa, nombre de la clase y el metodo y sus argumentos") al agregar el GreetingService.*(..) todas las clases que implementen esta interfaz
    //@Before("execution(String com.gerson.springboot.app.aop.springbootaop.services.GreetingService.sayHello(..))") //Punto de corte cual metodo o package vamos a interceptar. Execution recive("String: tipo de dato que devuelve el metodo, la ruta completa, nombre de la clase y el metodo y sus argumentos") todas las clases que implementen esta interfaz
    @Before("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
    public void loggerBefore(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @Before
        String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
        String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo

        logger.info("Antes:" + method + " con los argumentos " + args);
    }

    //After - Especifica que el metodo loggerAfter se ejecutara despues que el metodo especificado en execution
    @After("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
    public void loggerAfter(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @After
        String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
        String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo

        logger.info("Despues:" + method + " con los argumentos " + args);
    }


        //AfterReturning - Especifica que el metodo loggerAfterReturning se ejecutara despues que el metodo especificado en execution retorne
        @AfterReturning("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
        public void loggerAfterReturning(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @Before
            String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
            String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo
    
            logger.info("Despues de retornar:" + method + " con los argumentos " + args);
        }

    //AfterThrowing - Especifica que el metodo loggerAfterThrowing se ejecutara despues que el metodo especificado en execution tenga un error o lanze una excepcion
    @AfterThrowing("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
    public void loggerAfterTrowing(JoinPoint joinPoint){ //Este metodo se va a a ejecutar antes del metodo especificado en @Before
        String method = joinPoint.getSignature().getName(); //Obtenemos el nombre del metodo
        String args = Arrays .toString(joinPoint.getArgs());//Obtenemos los argumentos del metodo

        logger.info("Despues de lanzar la excepcion:" + method + " con los argumentos " + args);
    }


    //Con @Around envuelve todo, podemos implementar la funcionalidad del @Before, @After y el @AfterThrowing(cuando se lanza una excepcion o surge un error)
    @Around("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") 
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable{ //ProceedingJoinPoint se esta ejecutando este aspecto va a envolver la ejecucion
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        Object result = null; //valor vacio
        try {
            // Podemos hacer algo antes(Before) - Implementar el Before
            logger.info("El metodo " + method + "() con los parametros " + args);
            
            result = joinPoint.proceed();
            // Podemos hacer algo despues(After) - Implementar el After
            logger.info("El metodo " + method + "() retorna el resultado " + result);

            return result;

        } catch (Throwable e) {
            //Implementar el @AfterThrowing(cuando se lanza una excepcion o surge un error
            logger.error("Error en la llamada del metodo " +  method + "()");
            throw e;  //lanzamos la excepcion por lo que el se elimina el return resulta;
        }
        
        //return result; //Se comenta porque se agrego en el catch throw e. //Se puede eliminar throw e y dejar este return
        
    }


}
