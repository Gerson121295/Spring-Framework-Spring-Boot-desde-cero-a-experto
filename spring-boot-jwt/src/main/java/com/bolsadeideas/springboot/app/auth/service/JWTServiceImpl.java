package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {
	
	//Variables estaticas
	//Publica se puede acceder desde cualquiera, Static no es necesario crear la instancia para tener acceso, final no se puede modificar, String es el tipo, SECRET es el nombre de la variable, luego el valor.
	//public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes()); //prof esta deprecated
	public static final SecretKey SECRET_KEY = new SecretKeySpec("algunaLlaveSecretsfasfasfasfagabafaf".getBytes(), SignatureAlgorithm.HS512.getJcaName());
	public static final long EXPIRATION_DATE = 14000000L; //Fecha de expiracion
	public static final String TOKEN_PREFIX = "Bearer ";	//El Prefijo del token
	public static final String HEADER_STRING = "Authorization"; //es el "Authorization" de la clase JWTAuthenticationFilter y JWTAuthorizationFilter
	
	@Override
	public String create(Authentication auth) throws IOException {
		
		 //Obtenemos el username para generar el token
		String username = ((User) auth.getPrincipal()).getUsername(); //Obtenemos el username para generar el token
		Date fechaCreacion = new Date();
		Date fechaExpiracion = new Date(System.currentTimeMillis() + EXPIRATION_DATE);
			
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
                .signWith(SECRET_KEY)
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
				.setSigningKey(SECRET_KEY)
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
		if(token !=null && token.startsWith(TOKEN_PREFIX)) { //Valida si el token no es igual a null y si empieza por "Bearer "
		return token.replace(TOKEN_PREFIX, ""); //Remplaza el TOKEN_PREFIX por ""
		}
		return null; //Si no cumple la validacion retorna un null
	}
}






