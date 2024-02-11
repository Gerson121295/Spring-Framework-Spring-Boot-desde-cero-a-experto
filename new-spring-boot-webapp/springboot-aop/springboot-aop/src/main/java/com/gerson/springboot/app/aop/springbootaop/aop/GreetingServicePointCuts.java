package com.gerson.springboot.app.aop.springbootaop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GreetingServicePointCuts {

    //Metodo para definir el punto de corte para evitar repetir en cada metodo de la clase: GreetingAspect 
    @Pointcut("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") //punto de corte
    public void greetingLoggerPointCut(){}

    //Metodo para definir el punto de corte para evitar repetir en cada metodo de la clase: GreetingFooAspect
    @Pointcut("execution(* com.gerson.springboot.app.aop.springbootaop.services.GreetingService.*(..))") //punto de corte
    public void greetingFoodLoggerPointCut(){}

}
