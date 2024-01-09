package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;


public interface JWTService {
	
	//Metodo para crear el token
	public String create(Authentication auth) throws IOException;
	
	//Metodo para validar el token
	public boolean validate(String token);

	//Metodo para obtener los Claims
	public Claims getClaims(String token);
	
	//Metodo para obtener el Username y los roles
	public String getUsername(String token);
	
	//Metodo para obtener los roles
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
	
	//Metodo para resolver el token
	public String resolve(String token);
	
}