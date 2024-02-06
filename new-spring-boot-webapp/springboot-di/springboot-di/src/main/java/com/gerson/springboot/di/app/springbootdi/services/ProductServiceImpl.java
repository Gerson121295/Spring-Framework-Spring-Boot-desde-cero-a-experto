package com.gerson.springboot.di.app.springbootdi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gerson.springboot.di.app.springbootdi.models.Product;
import com.gerson.springboot.di.app.springbootdi.repositories.ProductRepository;

//El service (maneja la logica del negocio) accede a los datos mediante el repositorio y ademas trabaja con estos datos.
//@Component //Esta anotacion es generica por lo que tambien se puede anotar con esta anotacion para indicar a spring que esta clase es un componente
@Service //Esta anotacion indica a spring que esta clase es un servicio(maneja la logica del negocio)
public class ProductServiceImpl implements ProductService{

    //Forma 1: Para usar datos definidos en el archivo config.properties
    //@Autowired
    //private Environment environment; 

    //Forma 2: Para usar datos definidos en el archivo config.properties
    @Value("${config.price.tax}")
    private Double tax;

    //private ProductRepositoryImpl repository = new ProductRepositoryImpl(); //Forma 1 de Inyeccion del ProductRepository para acceder a los datos. Nosotros llamamos e instanciamos al objeto
    
    //@Autowired
    //private ProductRepositoryImpl repository; //Forma 2 de Inyeccion del ProductRepositoryImpl para acceder a los datos. // El contenedor nos llama a nosotros y nos provee el objeto, nos pasa una instancia que esta guarda en el contenedor con @Autowired
//Este principio de instanciar con Autowired se conoce como hollywood - No nos llames, Nosotros te llamamos. Es decir no crees la instancia no llames el objeto, nosotros te vamos a proveer el objeto a ti y lo inyecta.

    @Autowired
    //@Qualifier("productRepositoryImpl") //Especifica a la clase que Repositorio inyectar. Se puede agregar el nombre de la clase pero iniciando en minuscula
    //@Qualifier("productList") //Especifica a la clase que Repositorio inyectar. En este caso es ProducRespositoryImpl pero a la anotacion @Repository le agregue un nombre.
    @Qualifier("productJson")
    private ProductRepository repository; //Se implementa la interfaz usando el principio Holywood

    //Ejemplo de que se puede inyectar usando el metodo set de Repository
/*  private ProductRepository repository; //Se implementa la interfaz usando el principio Holywood
    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }
*/

/* 
//Ejemplo de inyeccion por medio del constructor
private ProductRepository repository;
public ProductServiceImpl(@Qualifier("ProductRepositoryImpl") ProductRepository repository) {
    this.repository = repository;
}
 */

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
/*   @Override
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
*/

//Ejemplo para anotaciones: @RequestScope y @SessionScope
    //Ejemplo mutacion cada vez que se da clic en listar productos el precio cambia, debido a que los datos estan en memoria, en caso de una BD no ocurriria eso.
    // Ejemplo para describir la anotacion @RequestScope en la clase: ProductRepositoryImpl la cual al usar esta anotacion la app ya no es singleton cada vez que se ejecuta aumenta, ahora  es por request, 
    //el ciclo de vida es por un request despues se limpia en memoria se autodestruye. Por lo que no se aumentará el precio cada vez que se liste los productos.
 
    @Override
    public List<Product> findAll(){
        //return repository.findAll(); //Obtiene toda la lista de productos
        //Obtiene toda la lista de productos pero multiplica los precios * 1.25d(decimal)
        return repository.findAll().stream().map(p -> {
           //Double priceTax = p.getPrice() * 1.25d;
           
        //Forma 1: Usando environment - Para usar datos definidos en el archivo config.properties
           //System.out.println(environment.getProperty("config.price.tax", Double.class));
          // @SuppressWarnings("null")
          // Double priceTax = p.getPrice() * environment.getProperty("config.price.tax", Double.class); //Multiplica el valor del Tax definido en el archivo config.properties

        //Forma 2: Definir en variable - Para usar datos definidos en el archivo config.properties
             System.out.println(tax);
             Double priceTax = p.getPrice() * tax; //Multiplica el valor del Tax definido en el archivo config.properties
                
        p.setPrice(priceTax.longValue());
           return p;
        }).collect(Collectors.toList()); //El map devuelve un stream por lo que con collect convertimos de stream a una Lista, ya que este metodo retorna una Lista
    }

    //Busca un producto por id
    @Override
    public Product findById(Long ind){
        return repository.findById(ind);
        
    }


}
