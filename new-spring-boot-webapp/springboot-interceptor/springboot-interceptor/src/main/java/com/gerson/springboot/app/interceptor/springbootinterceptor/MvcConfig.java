package com.gerson.springboot.app.interceptor.springbootinterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(timeInterceptor);

        //Interceptor para incluir o excluir rutas
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar");  //Solo en /bar se ejecuta el interceptor.
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar", "/app/foo");  //Incluir rutas: /bar y /foo se ejecuta el interceptor.
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/**");  //Incluir todas las rutas que se encuentren en /app/todas, se ejecuta el interceptor.
        
        registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar", "/app/foo");  //Excluir rutas: /bar y /foo se ejecuta el interceptor en /baz

    }

}
