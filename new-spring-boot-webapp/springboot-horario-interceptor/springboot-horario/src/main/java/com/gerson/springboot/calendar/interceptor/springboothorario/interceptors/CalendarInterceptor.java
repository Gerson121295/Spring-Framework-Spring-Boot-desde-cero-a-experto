package com.gerson.springboot.calendar.interceptor.springboothorario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("calendarInterceptor")
public class CalendarInterceptor implements HandlerInterceptor{

    //Inyeccion de los valores definido en el archivo application.properties
    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY); //Mi Hora actual en la cual estoy
                System.out.println(hour);

                //COmparacion si la hora de la peticion esta dentro de la hora de apertura, Si está se crea un mensaje y se envia al controller
                if(hour >= open && hour<close){
                    StringBuilder message = new StringBuilder("Bienvenidos al horario de atencion a clientes");
                    message.append(", atendemos desde las ");
                    message.append(open); //Hora de apertura
                    message.append("hrs.");
                    message.append(" hasta las ");
                    message.append(close);
                    message.append("hrs.");
                    message.append(" Gracias por su visita!");

                    request.setAttribute("message", message.toString());
                    return true;
                }

                //Caso de cuando se hace la peticion y no es horario de atencion, Devuelve el Json creado
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> data = new HashMap<>();
                StringBuilder message = new StringBuilder("Cerrado, fuera de horario de atención");
                message.append(" por favor visitenos desde las ");
                message.append(open);
                message.append(" y las ");
                message.append(close);
                message.append(" hrs. Gracias!");

                data.put("message", message.toString());   
                data.put("date", new Date().toString()); //muestre la fecha en string no TimeStamp

                response.setContentType("application/json");
                response.setStatus(401);
                response.getWriter().write(mapper.writeValueAsString(data));
        return false;
    }


    @SuppressWarnings("null")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        
    }

    
    


}
