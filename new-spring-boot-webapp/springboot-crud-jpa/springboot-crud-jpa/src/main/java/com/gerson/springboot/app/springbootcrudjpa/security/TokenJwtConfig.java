package com.gerson.springboot.app.springbootcrudjpa.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

//Clase guarda las Constantes para utilizarlas en diferentes clases: JwtAuthenticationFilter y JwtValidationFilter

public class TokenJwtConfig {

    //Estas variable si se usarán en la clase fueran: private como se define en esta clase externa debe ser publica para que se pueda acceder desde otra clase.
    //Generar llave secreta, que se queda en el servidor.
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    //Se usa variables para reemplar el texto:  "Bearer "  y "Authorization" en el codigo.
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String  HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

}
