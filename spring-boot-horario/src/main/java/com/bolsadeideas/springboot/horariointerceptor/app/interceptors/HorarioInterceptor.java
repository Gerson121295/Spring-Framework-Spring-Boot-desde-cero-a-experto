package com.bolsadeideas.springboot.horariointerceptor.app.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("horario")
public class HorarioInterceptor implements HandlerInterceptor {
	
	@Value("${config.horario.apertura}") //Pasa los valores de aplication.properties a la variable apertura
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//Obtenemos la hora actual
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		//Validamos si la hora se encuentra en la hora de inicio o fin
		if(hora >= apertura && hora < cierre) {
			
			//Si se cumple la condicion mostramos el mensaje
			StringBuilder mensaje = new StringBuilder("Bienvenidos al horario de atencion a clientes.");
			mensaje.append(", atendemos desde las ");
			mensaje.append(apertura);
			mensaje.append("hrs. ");
			mensaje.append("hasta las ");
			mensaje.append(cierre);
			mensaje.append("hrs. ");
			mensaje.append("Gracias por su visita. ");
			
			//Pasamos el mensaje al request, del inspector a la vista(la manejamos en el postHandler)
			request.setAttribute("mensaje", mensaje.toString());//"mensaje" es la variable con la cual se pasa a la vista, y concatenamos el mensaje
			
			return true;
		}
		
		//Si es falso redirecciona a otra ruta
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//Pasamos el mensaje a la vista
		String mensaje = (String) request.getAttribute("mensaje"); //Obtenemos el mensaje
		if(modelAndView != null && handler instanceof HandlerMethod) {
			modelAndView.addObject("horario", mensaje); //con modelAndView pasamos el mensaje a la vista index.html
		}

	}

}
