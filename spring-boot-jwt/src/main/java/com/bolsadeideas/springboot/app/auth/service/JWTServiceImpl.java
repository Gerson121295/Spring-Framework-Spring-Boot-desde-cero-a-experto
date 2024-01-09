package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.bolsadeideas.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JWTServiceImpl implements JWTService {

	@Override
	public String create(Authentication auth) throws IOException {
		
		 //Obtenemos el username para generar el token
		String username = ((User) auth.getPrincipal()).getUsername(); //Obtenemos el username para generar el token
		Date fechaCreacion = new Date();
		Date fechaExpiracion = new Date(System.currentTimeMillis() + 14000000L);
			
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();	
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
 /*       claims.put("authorities", authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new));
  */   	

		//Generamos el token
		String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(JWTAuthenticationFilter.SECRET_KEY)
                .setIssuedAt(fechaCreacion)
                .setExpiration(fechaExpiracion)
                .compact();
		//3600000=1hora (4horas = 3600000*4)  14000000L=2 horas
		
		return token;
	}
	

	@Override
	public boolean validate(String token) {
		
		//Implementacion de la validacion del Token	
					
				try {	
					
					getClaims(token);
					return true;
				} catch (JwtException | IllegalArgumentException e) {
					return false;
				}			
		}
				

	@Override
	public Claims getClaims(String token) {
		
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(JWTAuthenticationFilter.SECRET_KEY)
				.build().parseClaimsJws(resolve(token))
				.getBody();	
		return claims;
	}

	//Obtener el Username
	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	//Obtener el rol
	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
				
		return authorities;
	}

	@Override
	public String resolve(String token) {
		if(token !=null && token.startsWith("Bearer ")) { //Valida si el token no es igual a null y si empieza por "Bearer "
		return token.replace("Bearer ", "");
		}
		return null; //Si no cumple la validacion retorna un null
	}
}






