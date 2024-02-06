package com.gerson.springboot.di.app.springbootdi.repositories;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.gerson.springboot.di.app.springbootdi.models.Product;

//Repository es la capa de datos.

//@Component //para decir a spring que esta clase es un componente tambien se puede renombrar esta clase como componente esta anotacion es mas generica.

@Primary //Al anotar primary especifica a spring que este será el primer Repository o el principal dentro de otros, Solo puede haber una anotacion @Primary
//@Repository//para decir a spring que esta clase es un componente es un repositorio - Accede a datos 
@Repository("productList") //En caso que no me guste el nombre de la clase para agregarlo en la anotacion @Qualifier le agrego un nombre
//@RequestScope //Ya no es singleton cada vez que se ejecuta aumenta, ahora  es por request, el ciclo de vida es por un request despues se limpia en memoria se autodestruye.
//@SessionScope //Sesion Http dura mientras estamos trabajando en el navegador, esta anotacion dura la sesion hasta que se cierre la sesion o cierre el navegador.
public class ProductRepositoryImpl implements ProductRepository{
    private List<Product> data;

    //Metodo para definir productos en una lista
    //Al agregar la anotacion @RequestScope hace que este array data ya no se guarda en memoria en la aplicacion ya no es singleton si no que ahora es por usuario por request. Un usuario ejecuta y obtiene una vez luego otro usuario
    // Con la anotacion @SessionScope el arreglo de data se guarda en la sesion y se eliminara cuando se cierre la sesion o se cierre el navegador.
    public ProductRepositoryImpl(){
        this.data = Arrays.asList(
            new Product(1L, "Memoria corsair 32", 300L),
            new Product(2L, "CPU Intel Core i9", 850L),
            new Product(3L, "Teclado Razer 60%", 180L),
            new Product(4L, "Motherboard", 490L)
        );
    }

    //Metodo para devolver la data: Devuelve toda la lista de productos
    @Override
    public List<Product> findAll(){
        return data;
    }

    @Override
    public Product findById(Long id){
        return data.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(); //.orElseThrow() en caso de que no encuentre el producto lanza una excepcion de que no se encontro, si lo encuentra devuelve el producto.
    }


}




