package com.gerson.springboot.app.springbootcrudjpa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.springboot.app.springbootcrudjpa.entities.Product;
import com.gerson.springboot.app.springbootcrudjpa.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    //Inyeccion para Validar los atributos de la clase Product usando la clase: ProductValidation
    //@Autowired
    //private ProductValidation validationP;

    @GetMapping
    public List<Product> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        Optional<Product> productOptional = service.findById(id); //Buscamos el producto

        //validamos si el producto con ese id esta presente
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow()); //si esta presente muestra el objeto y return ok 
        }
        return ResponseEntity.notFound().build(); //Return si no encontro el producto con ese id
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){ //Valid para validar los atributos de Product definidos en la Clase Product, bindingResult contiene los mensajes de error que se optuvieron
        
        //validationP.validate(product, result); //Al crear: Ejemplo Valida los atributos de la clase Product usando la clase: ProductValidation

        //Este es el orden en el cual debe ir cuando se maneja validaciones: @Valid @RequestBody Product product, BindingResult result
        if(result.hasFieldErrors()){  //result Valida si tiene error en las validaciones
            return validation(result);
        }
        Product productNew = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productNew); //Otra forma agregarlo todo y eliminar 1 linea de codigo: .body(service.save(product));
    }

    //Forma 2 Optimizada de Update.
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){

        //validationP.validate(product, result); //Al Update: Ejemplo Valida los atributos de la clase Product usando la clase: ProductValidation

        //result Valida si tiene error en las validaciones
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<Product> producOptional = service.update(id, product);
        //Valida si el producto se actualizo
        if(producOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(producOptional.orElseThrow()); //Si esta presente muestra el producto y el estado OK
        }   
        return ResponseEntity.notFound().build(); //Return notFound si no encontro el producto con ese id
    } 

    //Forma 2 - Optimizada: Elimina por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Product> productOptional = service.delete(id); //eliminamos el producto y lo guardamos en productOptional
        //validamos si el producto con ese id esta presente
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow()); //return ok si encontro el producto
        }
        return ResponseEntity.notFound().build(); //Return notFound si no encontro el producto con ese id
    }


    // El metodo save se utiliza para guardar(Post) y actualizar(Put) - Para guardar no recibe id en el parametro(viene null) o puede recibir el id pero este debe ser null, Para actualizar recibe id en el parametro
/* Forma 1: Update
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
        product.setId(id); //Pasamos el id del producto que viene de parametro para eliminar
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }
*/


/* //Forma 1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        //Instancia del Producto
        Product product = new Product();
        product.setId(id); //Le asignamos el id del producto el cual se recibe en el parametro
        Optional<Product> productOptional = service.delete(product); //eliminamos el producto y lo guardamos en productOptional
        //validamos si el producto con ese id esta presente
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow()); //return ok si encontro el producto
        }
        return ResponseEntity.notFound().build(); //Return notFound si no encontro el producto con ese id
    }
*/

//Metodo de Validacion si existiera error en las validaciones de ingreso de datos del producto
//private ResponseEntity<Map> validation(BindingResult result) {   //Forma 1
private ResponseEntity<?> validation(BindingResult result) {   //Forma 2: Signo generico: <?> 
    Map<String, String> errors = new HashMap<>(); //Variable: errors guarda los errores que se encuentren
    //foreach recorre los campos si encuentra error muestra el mensaje
    result.getFieldErrors().forEach(err -> {
     errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());     
    }); 
    return ResponseEntity.badRequest().body(errors);
}

}
