package com.gerson.springboot.di.app.springbootdi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gerson.springboot.di.app.springbootdi.models.Product;
import com.gerson.springboot.di.app.springbootdi.repositories.ProductRepositoryImpl;

//El service (maneja la logica del negocio) accede a los datos mediante el repositorio y ademas trabaja con estos datos.
@Component //para decir a spring que esta clase es un componente
public class ProductServiceImpl implements ProductService{

    //private ProductRepositoryImpl repository = new ProductRepositoryImpl(); //Forma 1 de Inyeccion del ProductRepository para acceder a los datos. Nosotros llamamos e instanciamos al objeto
    @Autowired
    private ProductRepositoryImpl repository; //Forma 2 de Inyeccion del ProductRepository para acceder a los datos. // El contenedor nos llama a nosotros y nos provee el objeto, nos pasa una instancia que esta guarda en el contenedor con @Autowired
//Este principio de instanciar con Autowired se conoce como hollywood - No nos llames, Nosotros te llamamos. Es decir no crees la instancia no llames el objeto, nosotros te vamos a proveer el objeto a ti y lo inyecta.


      //Devueleve la lista de productos
/*  @Override
    public List<Product> findAll(){
        //return repository.findAll(); //Obtiene toda la lista de productos
        //Obtiene toda la lista de productos pero multiplica los precios * 1.25d(decimal)
        return repository.findAll().stream().map(p -> {
            Double priceTax = p.getPrice() * 1.25d;
            Product newProd = new Product(p.getId(), p.getName(), priceTax.longValue()); //Principio de inmutabilidad. cada vez que al listar los productos no se incremente o vuelve a multiplicar por 1.25
            return newProd;
        }).collect(Collectors.toList()); //El map devuelve un stream por lo que con collect convertimos de stream a una Lista, ya que este metodo retorna una Lista
    }
*/

    //Inmutabilidad utilizando la interfaz Clonable - Es igual que la anterior findAll() pero mas elegante.
    //Ir a la clase Product e implementar la interfaz clonable: implements Cloneable asi como el metodo clon
    @Override
    public List<Product> findAll(){
        //return repository.findAll(); //Obtiene toda la lista de productos
        //Obtiene toda la lista de productos pero multiplica los precios * 1.25d(decimal)
        return repository.findAll().stream().map(p -> {
            Double priceTax = p.getPrice() * 1.25d;
            Product newProd = (Product) p.clone();
            newProd.setPrice(priceTax.longValue());
            return newProd;
        }).collect(Collectors.toList()); //El map devuelve un stream por lo que con collect convertimos de stream a una Lista, ya que este metodo retorna una Lista
    }

    //Busca un producto por id
    @Override
    public Product findById(Long ind){
        return repository.findById(ind);
        
    }

}
