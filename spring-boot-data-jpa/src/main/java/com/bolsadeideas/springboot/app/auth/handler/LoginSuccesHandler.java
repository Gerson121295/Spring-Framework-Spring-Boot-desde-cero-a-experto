package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private MessageSource messageSource; //para agregar los textos de archivos messages_codigoIdioma.properties para las traducciones de titulos de los metodos del controlador
	
	@Autowired
	private LocaleResolver localeResolver;

	//Clase para notificar cuando se inicie sesion exitosamente al usuario
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		Locale locale = localeResolver.resolveLocale(request);
		String mensaje = String.format(messageSource.getMessage("text.login.success", null, locale), authentication.getName());
		 	
		//flashMap.put("success", "Hola " +authentication.getName()+ ", haz iniciado sesión con éxito!");
		flashMap.put("success", mensaje);
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {//validar si la autenticacion exitosa no es null
		//logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesion con éxito");
			logger.info(mensaje);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}




