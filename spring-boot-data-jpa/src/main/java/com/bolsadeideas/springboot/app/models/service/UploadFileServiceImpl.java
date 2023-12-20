package com.bolsadeideas.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileServiceImpl implements IUploadFileService {

	// Un debug para que muestre en consola los nombres de los directorios
	private final Logger log = LoggerFactory.getLogger(getClass());

	// Constante para reemplazar la palabra "uploads"
	private final static String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);

		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		
		//Ruta interna del proyecto no se recomienda guardar imagenes o archivos dentro del proyecto, el proyecto solo debe ser de lectura.
		//se guarda en la ruta fisica de nuestro proyecto que estamos desarrollando (elcodigo fuente) y no el compilado jar (o war) por lo que se tiene que estar actualizando la carpeta de nuestro proyecto para ver las fotos
		//Path directorioRecursos = Paths.get("src//main//resources//static/uploads"); //aqui se guardarán las imagenes
		//String rootPath = directorioRecursos.toFile().getAbsolutePath(); //Obtenemos en string la ruta de las fotos
		
		//Ruta externa - separada al proyecto al jar o war (Para guardar las fotos y archivos - Es la forma recomendable)
		//String rootPath = "C://Temp//uploads"; //Crear la carpeta uploads en esa ruta //En linux la ruta es: "/opt/uploads";
		
		
		//Agregar directorio absoluto y externo en raiz del proyecto: 
		//Ruta Completa del proyecto: C:/User/Escritorio/spring-boot-data-jpa/uploads  //crear la carpeta uploads en esa ruta

		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename); //Crear una nueva foto
		//Path rootAbsolutPath = rootPath.toAbsolutePath(); //El metodo get path retorna un absoluto por lo que se elimina
		
		//Un debug para que muestre en consola los nombres de los directorios
		log.info("rootPath: " + rootPath); //Path relativo al proyecto.
		//log.info("rootAbsolutPath: " + rootAbsolutPath); //Path absoluto: desde disco c hasta el nombre del archivo.
		

/*				//COdigo para Ruta externa al proyecto en disco C o ruta interna dentro del proyecto en carpeta static 
			byte[] bytes = foto.getBytes();
			Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			
			flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename()+ "'");			
			cliente.setFoto(foto.getOriginalFilename()); 	//Pasamos el nombre de la foto del cliente
*/				
			//Codigo para directorio absoluto y externo en raiz del proyecto
			//Files.copy(file.getInputStream(), rootAbsolutPath); //El metodo get path retorna un absoluto por lo que se elimina
			Files.copy(file.getInputStream(), rootPath);					
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		
		//Eliminar archivo de imagen cuando se elimina el cliente
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	//Metodo tiene la ruta del archivo donde se guarda las imagenes
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());	
	}

	@Override
	public void init() throws IOException { 
		Files.createDirectory(Paths.get(UPLOADS_FOLDER)); //Crea el directorio
	}
}


