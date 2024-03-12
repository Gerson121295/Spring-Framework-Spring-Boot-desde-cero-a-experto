package com.gerson.springboot.backend.backendproducts.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.gerson.springboot.backend.backendproducts.entities.Product;

/* Debido a esta dependencia Data Rest: spring-boot-starter-data-rest
Utiliza el paradigma HATEOAS son las siglas de "Hypermedia As The Engine Of Application State"
nuestro servicio debe proporcionar toda la informacion al cliente para que pueda interactuar con el backend
*/
//@RepositoryRestResource esta anotacion genera un CRUD controlador completo.
//@CrossOrigin(origins = "http://localhost:5173/") //para conectar el backend de SpringBoot con el fronted de React
//@CrossOrigin(origins = "http://localhost:4200/") //para conectar con angular
@CrossOrigin(origins = {"http://localhost:4200/", "http://localhost:5173/"}) //Acceso a React y angular
@RepositoryRestResource(path = "products") // automaticamente el cliente(postman o frontend) al pasar la ruta con un get obtiene los productos, pasan un delete con id elimina un producto y asi.
public interface ProductRepository extends CrudRepository<Product, Long>{  //recibe la clase, y su tipo de dato del id
}

