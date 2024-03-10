package com.gerson.springboot.backend.backendproducts;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.gerson.springboot.backend.backendproducts.entities.Product;

//Clase para configurar que en la clase ProductRepository muestre el id en los servicios a mostrar
@Configuration
public class DataRestConfig implements RepositoryRestConfigurer{

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Product.class); // expone los ids de la clase Product
    }


}
