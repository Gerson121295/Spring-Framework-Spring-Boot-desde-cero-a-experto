package com.gerson.springboot.app.springbootcrudjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@PropertySource("classpath:messages.properties") //para agregar archivos.properties  otra forma es agregarlos en una clase de configuracion: AppConfig
public class SpringbootCrudJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudJpaApplication.class, args);
	}



}
