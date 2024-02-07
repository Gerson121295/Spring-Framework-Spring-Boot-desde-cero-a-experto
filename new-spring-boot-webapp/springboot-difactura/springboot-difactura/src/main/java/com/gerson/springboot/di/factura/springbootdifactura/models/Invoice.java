package com.gerson.springboot.di.factura.springbootdifactura.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//Factura tiene un cliente y sus items

@Component
//@ApplicationScope //cuando hay otras aplicaciones dentro de tomcat
@RequestScope //para un componente cuyo ciclo de vida está vinculado a las solicitudes web actuales, //en este ejemplo el nombre pepe se repite cada vez que se hace la peticion
//@JsonIgnoreProperties({"targetSource", "advisors"})//Se agrego xq al agregar RequestScope surgieron error. //targetSource: Error en consola: No serializer found for class. advisors es otro error que se ignoró.
public class Invoice {

    @Autowired
    private Client client; //Como la clase Client es un componente se inyecta con: @Autowired
    //private Client client;

    @Value("${invoice.description}")
    private String description;

    @Qualifier("default")
    @Autowired //Se inyecta el bean que se configuro en la clase AppConfig
    private List<Item> items;

    //Constructor Vacio
    public Invoice() {
        System.out.println("Creando el componente de la factura 1."); //Se imprime en la consola
        //Cuando se ejecuta el constructor los valores de los atributos son nulos aun no tiene valor
        //El Constructor se ejecuta antes de que se halla instanciado e inyectado todos los atributos
        System.out.println(client);
        System.out.println(description);
    }


    //PostConstruct: Abajo de los atributos y constructor y antes de los Getters and Setters
    @PostConstruct
    public void init(){
        System.out.println("Creando el componente de la factura 2."); //Se imprime en la consola
        //Cuando se ejecuta el PostConstructor los valores de los atributos no son nulos, ya tiene valor
        //El postConstructor se ejecuta despues de que se halla instanciado e inyectado todos los atributos
        System.out.println(client);
        System.out.println(description);
        System.out.println();
        client.setName(client.getName().concat(" Pepe")); //se modifico el nombre del cliente se concateno Pepe
        description = description.concat(" del cliente: ").concat(client.getName()).concat(" ").concat(client.getLastname()); //Se modifico la descripcion
    }

    //PreDestroy (una vez cuando se destruye el concepto singleton) cuando bajamos el servicio(cerramos la app) se cumple una tarea(cerrar un recurso(archivos abiertos, cerrar BD), eliminar datos).
    @PreDestroy 
    public void destroy(){
        System.out.println("Destruyendo el componente o bean invoice!");
    }


     //Getters and Setters:  Los JSON que se visualizan en el navegor son creados con el metodo get
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    //Metodo muestra el total de la factura, suma los importes(Item)
    //Forma 1: For tradicional
/*     public int getTotal0(){
        int total = 0;

        for(Item item : items){ //items es la lista, por cada items, sumar por cada item
            total += item.getImporte(); //otra forma: total = total + item.getImporte();
        }
        return total;
    }
*/

    //Metodo muestra el total de la factura, suma los importes(Item)
    //Forma 2: Stream (Programacion funcional)
    public int getTotal(){
/*      int total = items.stream()
        .map(item -> item.getImporte()) //se convierte el flujo a entero ya que item era un objeto
        .reduce(0, (sum, importe) -> sum + importe); //sum es el acumulador de sum + importe
        return total;
 */ 
    //Otra forma mas reducida    
        return items.stream()
        .map(item -> item.getImporte()) //se convierte el flujo a entero ya que item era un objeto
        .reduce(0, (sum, importe) -> sum + importe); //sum es el acumulador de sum + importe
    }

}
