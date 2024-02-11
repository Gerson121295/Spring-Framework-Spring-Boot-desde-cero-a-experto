package com.gerson.springboot.calendar.interceptor.springboothorario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Clase de configuracion para agregar o registrar el Interceptor CalendarInterceptor

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Autowired
    @Qualifier("calendarInterceptor") //el aleas de la clase CalendarInterceptor
    private HandlerInterceptor calendar;


    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //Si queremos que le interceptor se agregue a toda las rutas de la aplicacio no agregamaso la linea de codigo siguente.
        registry.addInterceptor(calendar).addPathPatterns("/foo"); //El interceptor solo se agrega a la ruta "/foo"

    }

}
