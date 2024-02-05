package com.gerson.curso.springboot.webapp.springbootweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

/*  //configuraciond de varios archivos .properties 
@PropertySources({
	@PropertySource("classpath:values.properties"),
	@PropertySource("classpath:values2.properties") 
})
*/
//Configurar un archivo .properties //Para no tener esta configuracion en la clase principal podemos tener la configuracion en una clase configuracion, "ValuesConfig"
//@PropertySource("classpath:values.properties") //configuracion de un solo archivo values.properties
public class SpringbootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}

}
