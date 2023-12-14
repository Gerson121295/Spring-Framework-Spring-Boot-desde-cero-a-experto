package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor{
	
	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//Metodo para que no se ejecute el interceptor en el metodo Post - El retraso no se aplica al metodo post. Por lo que al enviar no aplicará
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		// Metodo de debug: Podemos obtener el detalle del metodo que se esta interceptando
		if(handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			logger.info("Es un controlador: " + metodo.getMethod().getName());
		}
		//-------------------
		
		logger.info("TiempoTranscurridoInterceptor: preHandle() entrando ...");
		
		//Calcular el tiempo de inicio
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio); //Guardamos y pasamos el tiempo inicio a la vista.
		
		//Simulando una demora (sobrecarga) o delay de manera random
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		
		return true;  //Para que siga con la ejecucion
		
		//Ejemplo si falla la intercepcion se reenvia a la ruta del login
		/*
		 response.sendRedirect(request.getContextPath().concat("/login")); //Si fal 
		 return false;  //El return true anterior se tendria que comentar.
		 */	
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//Metodo para que no se ejecute el interceptor en el metodo Post - El retraso no se aplica al metodo post. Por lo que al enviar no aplicará
		if(request.getMethod().equalsIgnoreCase("post")) {
			return;
		}
		
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		
		if(handler instanceof HandlerMethod &&  modelAndView != null) { //validamos que el tiempoTranscurrido no sea null
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido); //pasamos el tiempo transcurrido a la vista
		}
		
		logger.info("Tiempo Transcurrido: " + tiempoTranscurrido + " milisegundos");
		logger.info("TiempoTranscurridoInterceptor: postHandle() saliendo ...");
		
}

}



