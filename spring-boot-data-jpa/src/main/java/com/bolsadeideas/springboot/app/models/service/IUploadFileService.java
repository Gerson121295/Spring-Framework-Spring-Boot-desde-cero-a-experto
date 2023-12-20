package com.bolsadeideas.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

//Permite desacoplar el clienteController pasar la logica de subida de imagenes en esta Interfaz


public interface IUploadFileService {

	public Resource load(String filename) throws MalformedURLException; //en la clase que implementa la interfaz se trata la excepcion por lo que tambien se agrega en la interfaz throws MalformedURLException
	
	public String copy(MultipartFile file) throws IOException;
	
	public boolean delete(String filename);
	
	//Metodos al ejecutar la app se crea el directorio uploads y  al parar la app borra la carpeta uploads con las imagenes
	//Estos metodos se implementa en la clase UploadFileServiceImpl y la config se hace en SpringBootDataJpaApplication
	
	public void deleteAll();
	
	public void init() throws IOException;
	
}





