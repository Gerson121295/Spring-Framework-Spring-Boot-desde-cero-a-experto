package com.bolsadeideas.springboot.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}


/* Nota: 
  
 --Crear un proyecto de Spring Boot Web desde Eclipse:

CLic en File -> clic New -> Project -> Buscar y desplegar la carpeta Spring Boot -> 
Seleccionar: Spring Starter Project -> Agregar los datos del projecto:

Name:spring-boot-web
Type: Maven	Packaging: Jar
Java Version:17	Language: Java
Group: com.bolsadeideas.springboot.app
Artifact: spring-boot-web
Version: 0.0.1-SNAPSHOT
Descripcion: Demo project for Spring Boot
Package: com.bolsadeideas.springboot.web.app

Clic en Next: 

Seleccionar las dependencias del project:
Spring Boot Version: 3.2.0  (seleccionar la version de springBoot estable.

Dependencias Seleccionadas:
Spring Web
Thymeleaf
Spring Boot DevTools

Luego Clic en Finish.


Si da error al crear le project ir a la carpeta de eclipse y eliminar la carpeta .m2
luego  abrir el eclipse seleccionar el project clic en Maven y clic en Update Project
seleccionar el projecto y Ok.


-- Crear una vista a proyecto Spring boot web
- Clic en la carpeta templates -> New -> Other -> Web -> Seleccionar HTML File -> Agregar nombre: index.html (los nombres de las vistas siempre van en minusculas)
En la vista index: en meta cambiamos el charset a UTF-8: <meta charset="UTF-8" /> y nos aseguramos que la vista tenga ese caracter: damos clic derecho sobre la vista
index.html -> clic properties -> Revisamos que en Text File encoding tenga UTF-8 

 */



/*
 Para hacer el desplieque debemos tener creado una variable de entorno del sistema: llamado JAVA_HOME el cual debe tener la ruta del jdk que se usa en este caso es el 17
  
 //Despliegue y ejecución desde terminal(Deploy) 
 - Abrir el powerShell e Ir a la ruta del proyecto: 
 C:\Users\Hp01\eclipse-workspace\spring-boot-web
 y ejecutar: .\mvnw.cmd package
 
 PS C:\Users\Hp01\eclipse-workspace\spring-boot-web> .\mvnw.cmd package
 Listo no debera salir: BUILD SUCCESS
 
 Luego realizar el deploy ejecutando en el powershell: java -jar (buscar en la carpeta target el .jar creado)
 
 PS C:\Users\Hp01\eclipse-workspace\spring-boot-web> java -jar .\target\spring-boot-web-0.0.1-SNAPSHOT.jar
 
 Listo nuestro proyecto ha sido levantado y podemos probar en el navegador probando las rutas: http://localhost:8080/app/listar
 
 
 */









