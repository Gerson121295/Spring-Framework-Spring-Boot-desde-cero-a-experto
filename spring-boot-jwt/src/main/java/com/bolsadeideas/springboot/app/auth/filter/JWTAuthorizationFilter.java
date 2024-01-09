package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//Filtro valida si el rol del usuario tiene acceso a las rutas

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JWTService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		
		if(!requiresAuthentication(header)) { //si es distinto a requiresAuthentication continuamos con la ejecucion y salimos del filtro
			chain.doFilter(request, response);
			return;
		}
		
		//Implementacion de la validacion del Token
	
		
		UsernamePasswordAuthenticationToken authentication = null;
		
		if(jwtService.validate(header)){ //El header es el token
			
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));//Obtenemos el usuario y el rol
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
		
	}

	protected boolean requiresAuthentication(String header) {
		if(header == null || !header.startsWith("Bearer ")) { //si el header es nulo o no comienza con el Bearer
			return false;
		}
		return true;
	}
}





