package com.gerson.springboot.app.springbootcrudjpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gerson.springboot.app.springbootcrudjpa.entities.Product;
import com.gerson.springboot.app.springbootcrudjpa.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) { 
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    //Update optimizado
    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        //Busca el producto por id a actualizar
       Optional<Product> productOptional = repository.findById(id);

        //Valida si el id se encontro, si se encuentra edita los datos.
        if(productOptional.isPresent()){
            Product productDb = productOptional.orElseThrow(); //Obtiene el producto que se encuentra en productOptional con el orElseThrow si no encuentra el producto lanza una exception

            productDb.setSku(product.getSku());
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(repository.save(productDb)); //Devuelve el producto que se persistio en la BD
       }
       return productOptional;
    }

    //Forma 2: Optimizada: Eliminar solo por id
    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repository.findById(id); //Buscamos el product por el Id y guardamos el producto en productOptional
        productOptional.ifPresent(productDb -> { //Valida si esta presente el producto lo elimina
            repository.delete(productDb); //Elimina el producto
        });
        return productOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
       return repository.existsBySku(sku);
    }

/* //Forma 1:
    @Transactional
    @Override
    public Optional<Product> delete(Product product) {
        Optional<Product> productOptional = repository.findById(product.getId()); //Buscamos el product por el Id y guardamos el producto en productOptional
        productOptional.ifPresent(productDb ->{ //Valida si esta presente el producto lo elimina
            repository.delete(product); //Elimina el producto
        });
        return productOptional;
    }
*/


}
