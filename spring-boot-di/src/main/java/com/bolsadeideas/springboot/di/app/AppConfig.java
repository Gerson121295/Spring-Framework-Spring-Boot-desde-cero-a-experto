package com.bolsadeideas.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.bolsadeideas.springboot.di.app.models.domain.ItemFactura;
import com.bolsadeideas.springboot.di.app.models.domain.Producto;
import com.bolsadeideas.springboot.di.app.models.service.IServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicioComplejo;

//Registrando componentes usando clase de configuración y la anotación @Bean



@Configuration

@PropertySources({ //Permite agregar acento a las palabras en archivo properties
	    //@PropertySource(value = "classpath:texto.properties", encoding = "UTF-8"),
		@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
	})

public class AppConfig {
	
	@Primary  //Este servicio sera el principal
	@Bean("miServicioSimple") //Al Bean es opcional agregarle un nombre
	public IServicio registrarMiServicio() {
		return new MiServicio();
	}
	
	
	@Bean("miServicioComplejo") //Al Bean es opcional agregarle un nombre
	public IServicio registrarMiServicioComplejo() {
		return new MiServicioComplejo();
	}
	
	
	//Metodo para los items de la factura
	
	@Bean("itemsFactura")
	public List<ItemFactura> registrarItems(){
		Producto producto1 = new Producto("Camara Sony", 100);
		Producto producto2 = new Producto("Bicicleta Bianchi aro 26", 200);
		
		ItemFactura linea1 = new ItemFactura(producto1, 2);
		ItemFactura linea2 = new ItemFactura(producto2, 4);
		
		return Arrays.asList(linea1, linea2);
		
	}
	
	@Bean("itemsFacturaOficina")
	@Primary //Este bean se inyectará primero será el principal
	public List<ItemFactura> registrarItemsOficina(){
		Producto producto1 = new Producto("Monitor LG LCD 24", 250);
		Producto producto2 = new Producto("Notebook Asus", 500);
		Producto producto3 = new Producto("Impresora HP multifuncional", 80);
		Producto producto4 = new Producto("Escritorio Oficina", 300);
		
		ItemFactura linea1 = new ItemFactura(producto1, 2);
		ItemFactura linea2 = new ItemFactura(producto2, 1);
		ItemFactura linea3 = new ItemFactura(producto3, 1);
		ItemFactura linea4 = new ItemFactura(producto4, 1);
		
		return Arrays.asList(linea1, linea2, linea3, linea4);
		
	}
	
	
}




