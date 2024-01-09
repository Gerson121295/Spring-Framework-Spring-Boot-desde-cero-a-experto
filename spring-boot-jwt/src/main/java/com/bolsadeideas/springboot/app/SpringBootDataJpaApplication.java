package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner{
	//Interfaz CommandLineRunner para inicializar directorio uploads automaticamente, al ejecutar la app se crea el directorio uploads y 
	//y  al parar y volver a ejecutar la app borra la carpeta uploads con las imagenes

	@Autowired
	IUploadFileService uploadFileService; //Inyectamos la interfaz para aceder a los metodos que crean la carpeta uploads y borra el contenido de uploads anterior guardado al ejecutar la app.
	
	
	//Inyeccion del metodo BCryptPasswordEncoder de MvcConfig
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
		
		String password ="12345";
		
		//a partir de la contraseña password se generan 2 contraseñas encriptadas
		for(int i=0; i<2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}
		
	}

}


