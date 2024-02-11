package com.gerson.springboot.app.aop.springbootaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableAspectJAutoProxy //Para habilitar ASPECT AOP (solo en versiones menores hay que agregar esta anotacion)

@SpringBootApplication
public class SpringbootAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAopApplication.class, args);
	}

}
