package com.gerson.springboot.app.springbootcrudjpa.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.gerson.springboot.app.springbootcrudjpa.security.filter.JwtAuthenticationFilter;
import com.gerson.springboot.app.springbootcrudjpa.security.filter.JwtValidationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) //para habilitar La forma 2 de agregar el acceso en la ruta en el Controller usando anotaciones.
public class SpringSecurityConfig {

    @Bean //Un Bean es un Metodo que genera un componente de spring
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Configurando el filtro JwtAuthenticationFilter
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //Metodo para sincronizar con el filtro JwtAuthenticationFilter
    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager(); //authenticationConfiguration obtiene el AuthenticationManager de spring security
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //throws Exception puede lanzar algun tipo de exception error
        return http.authorizeHttpRequests((authz) -> authz
        //.requestMatchers("/api/users").permitAll() // todo los metodos dentro de la ruta /api/users es publica tiene acceso a los metodos listar, crear 

        .requestMatchers(HttpMethod.GET,"/api/users").permitAll() // solo los metodos Get en la ruta "/api/users" son de acceso publico
        .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll() //Metodo POST en la ruta "/api/users/register" son publico cualquiera puede registrarse, con usuario ROLE_USER

/*        // Forma 1 de agregar seguridad - definiendo las rutas y el rol que puede acceder - La forma 2 es agregar el acceso en la ruta del Controller usando anotaciones.
        //  Nota el role se define: "ADMIN" no se define como esta en la BD: "ROLE_ADMIN" debido a que .hasRole o .hasAnyRole le agrega el prefijo: "ROLE_"
        .requestMatchers(HttpMethod.POST,"/api/users").hasRole("ADMIN") //solo rol admin podra crear users con ROLE_ADMIN
        //.requestMatchers(HttpMethod.GET,"/api/products", "/api/products/{id}").hasAnyRole("ADMIN", "USER") //solo rol admin podra crear users con ROLE_ADMIN - Este ejemplo es definiendo en la BD una tabla ruta que este relacionada con roles 
        
        .requestMatchers(HttpMethod.POST,"/api/products").hasRole("ADMIN") //solo rol admin podra crear productos
        .requestMatchers(HttpMethod.PUT,"/api/products/{id}").hasRole("ADMIN") //solo rol admin podra modificar productos
        .requestMatchers(HttpMethod.DELETE,"/api/products/{id}").hasRole("ADMIN") //solo rol admin podra eliminar productos
*/
        .anyRequest().authenticated())  //cualquier otra ruta se necesita autenticacion. No tienen acceso.
        
        .addFilter(new JwtAuthenticationFilter(authenticationManager())) //Forma 1 llama al metodo authenticationManager. Forma 2 (authenticationConfiguration.getAuthenticationManager())) //Implementamos el filtro login para autenticacion del usuario.
        
        .addFilter(new JwtValidationFilter(authenticationManager())) //Filtro de validacion

        .csrf(config -> config.disable()) //csrf evita ejecucion de comando malicioso,hack en el formulario y session managment todo se maneje en el token 
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) //en cors se inyecta el metodo: corsConfigurationSource()
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //por defecto el SessionCreationPolicy es el Http donde se guarda la sesion del usuario es conectado.
        //STATELESS "Sin estado " no queda autenticado en una sesion Http, si no que Cada vez que se haga un request se tiene que mandar el token y datos del usuario para que se pueda autenticar y obtener la data
        .build();
    }


    //Metodos para configurar el CORS - Intercambio de origen cruzado para conexion entre el backend y el frontend, 
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration  config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        //Instancia de url de la implementacion concreta
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); //Esta configuracion se aplica a todas las rutas de la app.
        return source;
    }

    //Filtro de Spring se ejecute en todas las rutas
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter (){
        @SuppressWarnings("null")
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
            new CorsFilter(corsConfigurationSource()));
        //Prioridad
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}


//Filtro para Login - authenticar y Generar el Token
/*  Endpoint POST: localhost:8080/login   |JSON a enviar: { "username":"admin", "password":12345 }
    - Se optiene el token
    Luego al querer ingresar a una ruta protegida que necesite autorizacion ahi se debe
    pegar el token: 
    Ruta protegida: GET - http://localhost:8080/api/products
    Clic en Authorization -> Elegir "Bearer Token" el tipo de token en Type:
    Luego pegar el token que se genero al hacer el login.
    Luego clic en send y se obtendra el recurso. Listo.

*/
