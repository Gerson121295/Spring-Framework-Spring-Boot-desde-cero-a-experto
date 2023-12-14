package com.bolsadeideas.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	
	@Autowired
	@Qualifier("tiempoTranscurridoInterceptor")
	private HandlerInterceptor tiempoTranscurridoInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//registry.addInterceptor(tiempoTranscurridoInterceptor); //Intercepta a todas las rutas
		//registry.addInterceptor(tiempoTranscurridoInterceptor).addPathPatterns("/form/**", "/admin/usuarios"); //Se establecen las rutas a interceptar 
		
		registry.addInterceptor(tiempoTranscurridoInterceptor).addPathPatterns("/form/**"); //Se establece la ruta a interceptar /form/y lo que vengan
		
	}

}


