package com.gerson.springboot.di.factura.springbootdifactura;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.gerson.springboot.di.factura.springbootdifactura.models.Item;
import com.gerson.springboot.di.factura.springbootdifactura.models.Product;

@Configuration
@PropertySource(value= "classpath:data.properties", encoding = "UTF-8") //configura el archivo .propertiies para utilizar
public class AppConfig { //Esta es una clase para configuraciones


    //Metodo va a devolver un tipo List de Item de Producto y cantidad
    //@Primary
    //@Bean
    @Bean("default") //al agregar default se asigna nombre del @Bean para que en la clase Invoice en la anotacion de @Qualifier se agregue el nombre del bean llamado default y no el nombre del metodo.
    List<Item> itemsInvoice(){

        //Ejemplo de crear un producto utilizando el metodo set
/*      Product p1 = new Product();
        p1.setName("Camera Sony");
        p1.setPrice(800);
 */
        //Ejemplo de crear producto utilizando el constructor
        Product p1 = new Product("Camera Sony", 800);
        Product p2 = new Product("Bicicleta Bianchi", 1200);

        //Item recibe el producto y la cantidad
        List<Item> items = Arrays.asList( 
            new Item(p1, 2), 
            new Item(p2, 4)
            );

        return items;

        
/*  // Otra forma de escribirlo
        return Arrays.asList(new Item(new Product("Camera Sony", 800), 2), 
        new Item(new Product("Bicicleta Bianchi", 1200), 4));
*/

    }


    //Metodo va a devolver un tipo List de Item de Producto y cantidad
    @Bean
   List<Item> itemsInvoiceOffice(){

        //Ejemplo de crear producto utilizando el constructor
        Product p1 = new Product("Monitor Asus 24", 700);
        Product p2 = new Product("Notebook Razer", 2400);
        Product p3 = new Product("Impresora Hp", 800);
        Product p4 = new Product("Escritorio Oficina", 900);


        return Arrays.asList(new Item(p1, 4), 
                             new Item(p2, 6),
                             new Item(p3, 1),
                             new Item(p4, 4)
                             );

    }
}
