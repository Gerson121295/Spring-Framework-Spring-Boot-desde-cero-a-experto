package com.gerson.springboot.di.app.springbootdi.services;

import java.util.List;
import java.util.stream.Collectors;

import com.gerson.springboot.di.app.springbootdi.models.Product;
import com.gerson.springboot.di.app.springbootdi.repositories.ProductRepository;

//El service (maneja la logica del negocio) accede a los datos mediante el repositorio y ademas trabaja con estos datos.
public class ProductService {

    private ProductRepository repository = new ProductRepository(); //Inyeccion del ProductRepository para acceder a los datos.

    //Devueleve la lista de productos
/*    public List<Product> findAll(){
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
    public Product findById(Long ind){
        return repository.findById(ind);
        
    }

}
