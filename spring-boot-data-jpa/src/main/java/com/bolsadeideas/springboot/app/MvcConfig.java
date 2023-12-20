package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	//Un debug para que muestre en consola los nombres de los directorios
	private final Logger log = LoggerFactory.getLogger(getClass());

/*	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
	//Codigo para subir imagenes y guardarlas en Ruta externa o ruta dentro del proyecto	
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//Forma 1: Ruta externa donde se guardarán los archivos - separada al proyecto al jar o war (Para guardar las fotos y archivos - Es la forma recomendable)
		//Ruta externa - "C://Temp//uploads"; //Crear la carpeta uploads en esa ruta //En linux la ruta es: "/opt/uploads";
		//registry.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/Temp/uploads/"); //En linux: "file:/opt/uploads/"

		//Forma 2: Agregar directorio absoluto y externo en raiz del proyecto: 
		//Ruta Completa del proyecto: C:/User/Escritorio/spring-boot-data-jpa/uploads
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		log.info(resourcePath);		
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);		
	}
*/	
	
}


