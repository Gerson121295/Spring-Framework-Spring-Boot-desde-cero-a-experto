package com.gerson.springboot.di.app.springbootdi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import com.gerson.springboot.di.app.springbootdi.repositories.ProductRepository;
import com.gerson.springboot.di.app.springbootdi.repositories.ProductRepositoryJson;


@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    //Forma 1 de importa el archivo.json, no funciona porque esta clase no es un compónente. //SOLO ES Ejemplo
    @Value("classpath:json/product.json")
    private Resource resource; 


    //Registrando componente en clase anotada con @Configuration
    //Metodo a usar en ProductRepositoryJson
   // @Bean //al anotar con bean para crear un componente en Spring
   @Bean("productJson")
    //@Primary //Al anotar primary especifica a spring que este será el primer Repository o el principal dentro de otros, Solo puede haber una anotacion @Primary
    //Si queremos implemetar este repository por defecto el @Qualifier es el nombre del metodo: productRepositoryJson o le agregamos el nombre en el @Bean
    ProductRepository productRepositoryJson(){ 
        return new ProductRepositoryJson(resource); //se envia resource 
    }


}
