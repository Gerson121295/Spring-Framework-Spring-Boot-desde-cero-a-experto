package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
	
	
	//Metodo para registrar un controlador de vista un ViewController - Para mostrar la vista 404 a usuarios que no tengan permiso para ver una ruta
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403"); //"error_403" es la Vista a mostrar 
	}
	
	//Metodo conectado a SpringSecurityConfig
		@Bean
		public static BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		
		//Sistema multilenguaje y el locate
		//Guarda el parametro del lenguaje y se guarda en la session
		@Bean
		public LocaleResolver localeResolver() {
			//SessionLocaleResolver localeResolver = new SessionLocaleResolver(); //este no mantiene el idioma elegido, al hacer logout vuelve al español por defecto
			CookieLocaleResolver localeResolver = new CookieLocaleResolver(); //para que mantenga el idioma elegido despues de hace logout.
			localeResolver.setDefaultLocale(new Locale("es","ES")); //Por defecto nuestro sitio web estará con idioma español: es _ES
			return localeResolver;
		}
	
		//Crear el interceptor: para que cambie de idioma cuando se cambie el codigo del lenguaje. Recibe el parametro del lenguaje a cambiar
		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
			LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
			localeInterceptor.setParamName("lang"); //lang es el parametro que recibe el interceptor: ejemplo recibe "es","ES" para cambiar de idioma. 
			return localeInterceptor;
		}

		
		//Registrar este interceptor: clic derecho source -> override/implement Methods luego clic en addInterceptors
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(localeChangeInterceptor()); //se envia el interceptor localeChangeInterceptor()
		}
		
		
		//Para convertir el objeto a XML - Exportar a XML
		//Este bean sirve para convertir el objeto entity en un documento XML
		@Bean
		public Jaxb2Marshaller jaxb2Marshaller() {
			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
			marshaller.setClassesToBeBound(new Class[] {com.bolsadeideas.springboot.app.view.xml.ClienteList.class}); //se agrega la clase que tiene los datos que se convertirán a xml
			return marshaller;
		}
		
}





