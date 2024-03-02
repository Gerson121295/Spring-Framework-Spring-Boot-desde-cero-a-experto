package com.gerson.springboot.app.springbootcrudjpa.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerson.springboot.app.springbootcrudjpa.security.SimpleGrantedAuthorityJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//importa la clase TokenJwtConfig que contiene las variables static constantes sobre Security para esta clase
import static com.gerson.springboot.app.springbootcrudjpa.security.TokenJwtConfig.*;


//Clase - Filtro para validar el token JwtValidationFilter
public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager); //BasicAuthenticationFilter tiene un constructor que recibe el authenticationManager. La idea es que pase authenticationManager a la clase padre por eso usa super 
    }

    //Se implementa el metodo - doFilterInternal / Hacer filtro interno
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
                //Se obtiene el token o la cabecera el token se envia en la cabecera
                String header = request.getHeader(HEADER_AUTHORIZATION); //HEADER_AUTHORIZATION variable statica definida en la clase TokenJwtConfig
                
                //si header es null o no comienza con la palabra "Bearer " nos salimos.
                if(header == null || !header.startsWith(PREFIX_TOKEN)){
                    chain.doFilter(request, response);
                    return;
                }

                //Reemplazamos el contenido de PREFIX_TOKEN que es "Bearer " por nada "" esto para dejar solo el token
                String token = header.replace(PREFIX_TOKEN, "");

                //Validar
                try {
                    Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build() //Verificamos y le pasamos la clave secreta
                                    .parseSignedClaims(token) // pasamos el token que anterior se le quito la palabra "Bearer " para pasar solo el token 
                                    .getPayload();   
                            //String username = claims.getSubject(); //Forma 1 de obtener el username
                            String username2 = (String) claims.get("username");   //Forma 2 de obtener el username           

                            //Obtenemos los roles
                            Object authoritiesClaims = claims.get("authorities"); //tenemos de tipo el JSON de tipo String
                           
                            //Convierte el JSON de tipo String a Collection
                            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                                new ObjectMapper()
                                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)  //se agrego la clase: SimpleGrantedAuthorityJsonCreator donde se establecio que role es ("authority") ya que en authoritiesClaims viene como authority
                                
                                .readValue(authoritiesClaims.toString(), 
                                SimpleGrantedAuthority[].class));

                            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username2, null, authorities); //solo validamos el token: 
                            //Autenticar
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            chain.doFilter(request, response);

                        } catch (JwtException e) {
                            //Manejar errores
                            Map<String, String> body = new HashMap<>();
                            body.put("error", e.getMessage());
                            body.put("message", "El token JWT es invalido");

                            //Escribimos la respuesta
                            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                            response.setStatus(HttpStatus.UNAUTHORIZED.value()); //Otra forma: (401);
                            response.setContentType(CONTENT_TYPE);
                }
                
                
                                    
        }

    


}
