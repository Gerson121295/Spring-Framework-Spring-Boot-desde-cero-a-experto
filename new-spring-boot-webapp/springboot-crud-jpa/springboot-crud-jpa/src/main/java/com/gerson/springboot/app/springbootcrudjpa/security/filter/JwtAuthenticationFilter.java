package com.gerson.springboot.app.springbootcrudjpa.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerson.springboot.app.springbootcrudjpa.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//importa la clase TokenJwtConfig que contiene las variables static constantes sobre Security para esta clase
import static com.gerson.springboot.app.springbootcrudjpa.security.TokenJwtConfig.*;

//Filtro para Login - authenticar y Generar el Token
// Endpoint POST: localhost:8080/login   |JSON a enviar: { "username":"admin", "password":12345 }

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
 
    }

    //Se implementa el metodo attemptAuthentication - Intento de autenticación
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) //Recibimos el request y el response, mediante el request obtenemos el Username y el password
            throws AuthenticationException {

                User user = null;
                String username = null;
                String password = null;

                try {
                    //el objectMapper toma la info de String y lo convierte a un Objecto de tipo User.class 
                    user = new ObjectMapper().readValue(request.getInputStream(), User.class);

                    //Se pasa el Username y el password
                    username = user.getUsername();
                    password = user.getPassword();

                } catch (StreamReadException e) { //Maneja el error de lectura del Stream, que no puede pasar los valores de JSON a User
                    e.printStackTrace();
                } catch (DatabindException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UsernamePasswordAuthenticationToken autenticaciónToken = new UsernamePasswordAuthenticationToken(username, password);
                
                //Retornamos la authenticacion
                return authenticationManager.authenticate(autenticaciónToken); //authenticate llama al JpaUserDetailsService
    }
    
    //Implementacion de metodo Autenticación Exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
      
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal(); //Tipo User (usuario de security) es user (guarda) = el (User) de security JpaUserDetailsService que se obtiene a travez de authResult.getPrincipal().
            String username = user.getUsername(); //Obtenemos el username a traves de user.getUsername()
            
            //Obtenemos los roles del usuario
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

            //Claims son datos - se agrega los roles y username a claims
            Claims claims = Jwts.claims()
                    .add("authorities", new ObjectMapper().writeValueAsString(roles)) //pasamo los roles como un JSON
                    .add("username", username)
                .build();

            //Generar el token y sus datos: recibe el username y la clave secreta: SECRET_KEY
            String token = Jwts.builder()
                    .subject(username) //recibe el username del usuario
                    .claims(claims)
                    .expiration(new Date(System.currentTimeMillis() + 3600000)) // System.currentTimeMillis() (agregar la fecha actual) +  3600000 (1 horas - La cantidad de horas que va a durar el token)
                    .issuedAt(new Date()) //Fecha en la que se crea el token
                    .signWith(SECRET_KEY) //recibe la clave secreta generada, se firma el token
                    .compact();

            //Devolver el token al Cliente
            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token); //"Authorization", "Bearer " + token 

            //Devolvemos el token al cliente en formato JSON
            Map<String, String> body = new HashMap<>();
            body.put("token", token); //pasamos el token
            body.put("username", username);  //pasamos el username
            body.put("message", String.format("Hola %s has iniciado sesion con exito", username));  // %s mostrara el valor de la variable username
                
            //Escribir el JSON creado en la respuesta
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200); //status 200 ok
        }


    //Se implementa el metodo Autenticación Fallida
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

            Map<String, String> body = new HashMap<>();

            //Como buena practica no se debe especificar si el username o el password es el incorrecto
            body.put("message", "Error en la autentication username o password incorrectos!");
            body.put("error", failed.getMessage());
            
            //Respuesta - Convertir el body a un JSON
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401); //Petición (request) no ha sido ejecutada porque carece de credenciales válidas de autenticación para el recurso solicitado
            response.setContentType(CONTENT_TYPE);
       
    }

        

}
