package com.gerson.springboot.app.springbootcrudjpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties") //para agregar archivos.properties  otra forma es agregarlos en la clase main
public class AppConfig {

}
