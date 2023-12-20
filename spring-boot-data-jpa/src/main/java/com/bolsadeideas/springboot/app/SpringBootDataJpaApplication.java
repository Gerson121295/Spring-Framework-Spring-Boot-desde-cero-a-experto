package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner{
	//Interfaz CommandLineRunner para inicializar directorio uploads automaticamente, al ejecutar la app se crea el directorio uploads y 
	//y  al parar y volver a ejecutar la app borra la carpeta uploads con las imagenes

	@Autowired
	IUploadFileService uploadFileService; //Inyectamos la interfaz para aceder a los metodos que crean la carpeta uploads y borra el contenido de uploads anterior guardado al ejecutar la app.
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}


