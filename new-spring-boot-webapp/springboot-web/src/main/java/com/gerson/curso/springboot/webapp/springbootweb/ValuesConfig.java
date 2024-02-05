package com.gerson.curso.springboot.webapp.springbootweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//Clase para configuracio
@Configuration
/*  //configuraciond de varios archivos .properties 
@PropertySources({
	@PropertySource(value="classpath:values.properties", encoding = "UTF-8"),
	@PropertySource("classpath:values2.properties") 
})
*/
//Configurar un archivo .properties //Para no tener esta configuracion en la clase principal podemos tener esta clase de configuracion.
//@PropertySource("classpath:values.properties") //configuracion de un solo archivo values.properties
@PropertySource(value="classpath:values.properties", encoding = "UTF-8") //configuracion de un solo archivo values.properties, Agregar encoding para que el archivo acepte acentos y ñ
/* Nota: Otra forma de evitar errores con aceptacion de acentos y ñ en archivos .properties o plantillas html Visual studio Code es ubicarnos el archivo value.properties y 
hasta abajo en visual studio aparece UTF-8 dar clic ahi y clic en guardar con encoding y seleccionar Western (ISO 8859-1) y luego volver a escribir el texto con acentos.
*/
public class ValuesConfig {

}
