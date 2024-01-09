package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//https://stackoverflow.com/questions/41975045/how-to-design-a-good-jwt-authentication-filter

//Filtro autenticacion sirve cuando vamos a iniciar sesion.
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
//public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	/*
	public static Key getSecretKey() {
		return SECRET_KEY;
		}
	*/
	
	//Esta funciona 
	//public static final SecretKey SECRET_KEY = new SecretKeySpec("algunaLlaveSecretsfasfasfasfagabafaf".getBytes(), SignatureAlgorithm.HS512.getJcaName());
	
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		
		this.jwtService = jwtService; //el primer this.jwtService es el atributo de la clase el segundo es la igualacion al parametro que se pasa en el constructor
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if(username != null && password != null) {

			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
			
			}else{//No se envia el user y password en la url
				Usuario user = null;
				try {
					user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
					
					username = user.getUsername();
					password = user.getPassword();
					
					logger.info("Username desde request InputStream (raw): " + username);
					logger.info("Password desde request InputStream (raw): " + password);
					
				} catch (StreamReadException e) {
					e.printStackTrace();
				} catch (DatabindException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		username = username.trim();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
			
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                FilterChain chain, Authentication authResult) throws IOException {
	        
	   //Creacion del token
		String token = jwtService.create(authResult);
		
		//Pasar el token en la respuesta del usuario
		 response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

		//pasar el token en una estructrua Json
	        Map<String, Object> body = new HashMap<>();
	        body.put("token", token);
	        body.put("user", authResult.getPrincipal());
	        body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", ((User) authResult.getPrincipal()).getUsername())); //obtiene el username

	      //Pasar los datos(el cuerpo) a la respuesta
	        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
	        response.setStatus(200);
	        response.setContentType("application/json");
	     
	      //super.successfulAuthentication(request, response, chain, authResult);
	}

	
	//Authenticacion insatisfactoria
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		 Map<String, Object> body = new HashMap<>();
		 body.put("mensaje", "Error de autenticación: Username o password incorrecto!");
		 body.put("error", failed.getMessage());
		
		//Pasar los datos(el cuerpo) a la respuesta
	        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
	        response.setStatus(401); //401 No Autorizado y 403 Acceso Prohibido
	        response.setContentType("application/json");
		//super.unsuccessfulAuthentication(request, response, failed);
	}

	
	
	
}


