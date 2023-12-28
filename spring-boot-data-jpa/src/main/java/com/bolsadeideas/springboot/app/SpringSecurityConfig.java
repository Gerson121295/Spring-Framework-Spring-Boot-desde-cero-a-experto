package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) //Agregar seguridad en el controlador usando anotaciones @Secured o @PreAuthorize
//@EnableMethodSecurity() si lo definimos solo asi entonces solo acepta la anotacion @Secured en los controlador o metodos pero al asignarle los parametros: (securedEnabled = true, prePostEnabled = true) entonces podemos usar la anotacion @PreAuthorize que es lo mismo que @Secured 
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccesHandler successHandler;

/*	//movemos este metodo BCryptPasswordEncoder a la clase MvcConfig
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
*/
	//Inyeccion del metodo BCryptPasswordEncoder de MvcConfig
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; //Encriptar la contraseña
	
	 @Autowired
	 private DataSource dataSource; //Para la conexion a la BD
	 
	 
	 
	//Creacion de usuario Local - Memory authentication
/*	@Bean
	public UserDetailsService userDetailsService() throws Exception {

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

		manager.createUser(User
				.withUsername("gerson")
				//.password(passwordEncoder().encode("123"))  // metodo BCryptPasswordEncoder en esta clase
				.password(this.passwordEncoder.encode("123")) // metodo BCryptPasswordEncoder en la clase MvcConfig
				.roles("USER").build());

		manager.createUser(
				User.withUsername("admin")
				//.password(passwordEncoder().encode("123"))
				.password(this.passwordEncoder.encode("123"))
				.roles("ADMIN", "USER").build());

		return manager;
	}
*/
	 
		//Code Profe: Spring Security - Autenticacion JDBC usando BD las tablas users y authorities ya fueron creadas y relacionadas
/*	 @SuppressWarnings("removal") 	
	 @Bean
	    AuthenticationManager authManager(HttpSecurity http) throws Exception{
	        return 	http.getSharedObject(AuthenticationManagerBuilder.class)
	                .jdbcAuthentication()
	                .dataSource(dataSource)
	                .passwordEncoder(passwordEncoder)
	                .usersByUsernameQuery("select username, password, enabled from users where username=?") //valida si el usuario y password es igual al que se envia en el formulario al de la BD
	                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?")
	                .and().build();            
	    }
*/

	 
		//Spring Security - Autenticacion JDBC usando BD las tablas users y authorities ya fueron creadas y relacionadas
	@Bean
	public UserDetailsService userDetailsService(AuthenticationManagerBuilder build) throws Exception {
	
		build.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
			return build.getDefaultUserDetailsService();
	}

	 

	 

	// Autorizacion a Rutas  ACL 
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests(auth -> auth
					//Rutas Publicas
					.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
					 
					//Rutas Privadas necesitan que el usuario se autentique
	//Se comento las rutas que requieren autorizacion para cambiar la autorizacion: Agregar seguridad en el controlador usando anotaciones @Secured o @PreAuthorize
					/*.requestMatchers("/uploads/**").hasAnyRole("USER")*/
                   /* .requestMatchers("/ver/**").hasRole("USER") */
                   /* .requestMatchers("/factura/**").hasRole("ADMIN")*/
                   /* .requestMatchers("/form/**").hasRole("ADMIN")*/
                   /* .requestMatchers("/eliminar/**").hasRole("ADMIN")*/
					.anyRequest().authenticated());
			
			 //Login y logout a todos los usuarios
			http.formLogin(fL -> fL.successHandler(successHandler)
									.loginPage("/login")
								.permitAll());
			http.logout(lOut -> {
				lOut.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
						.permitAll();
				
			//- Para mostrar la vista 404 a usuarios que no tengan permiso para ver una ruta
				try {
					http.exceptionHandling(registry -> registry.accessDeniedPage("/error_403"));
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
			});
				
			http.httpBasic(Customizer.withDefaults());
			return http.build();
		}


	
	
/*	// Code instructor
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> {
                try {
                	//Rutas Publicas
                    authz.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
                    //Rutas Privadas necesitan que el usuario se autentique
                        .requestMatchers("/uploads/**").hasAnyRole("USER")
                        .requestMatchers("/ver/**").hasRole("USER")
                        .requestMatchers("/factura/**").hasRole("ADMIN")
                        .requestMatchers("/form/**").hasRole("ADMIN")
                        .requestMatchers("/eliminar/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                    //Login y logout a todos los usuarios
                    	.and()
                        .formLogin()
                        	.successHandler(successHandler)
                        	.loginPage("/login")
                        	.permitAll()
                        .and()
                        .logout().permitAll()
                        .and()
                        .exceptionHandling().accessDeniedPage("/error_403");
 
                } catch (Exception e) {
                        e.printStackTrace();
                }
            });
 
        return http.build();          
    }
*/
	

		
/*//Otra forma
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  http.authorizeHttpRequests(request ->
	          request
	              .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
	              .requestMatchers("/client/{id}").hasAnyRole("USER")
	              .requestMatchers("/uploads/**").hasAnyRole("USER")
	              .requestMatchers("/client/**").hasAnyRole("ADMIN")
	              .requestMatchers("/invoice/**").hasAnyRole("ADMIN")
	              .requestMatchers("/form/**").hasAnyRole("ADMIN")
	              .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
	              .requestMatchers("/factura/**").hasAnyRole("ADMIN")
	              .anyRequest().authenticated())
	  	  .formLogin(login -> {
	  		login.permitAll();
	  		login.loginPage("/login");
	  	  })
	  	  .logout(logout -> logout.permitAll())
	  	  .exceptionHandling(registry -> registry.accessDeniedPage("/error_403"));
	  	  
	  return http.build();
	}
 
}	
		
		
*/
		
		
		
}





