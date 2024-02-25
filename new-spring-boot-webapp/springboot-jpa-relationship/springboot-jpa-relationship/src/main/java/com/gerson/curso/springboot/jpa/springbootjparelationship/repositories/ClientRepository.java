package com.gerson.curso.springboot.jpa.springbootjparelationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Client;

//No se anota la clase porque cuando se extiende de CrudRepository ya es una clase de tipo component
public interface ClientRepository extends CrudRepository<Client, Long>{

    //Usar el joim trae los clientes que tenga factura y las facturas que tengan clientes.
    //Usar el left joim trae los clientes que tengan o no facturas

    //Para buscar por id
    //Buscar al cliente y sus direcciones en una sola consulta. Para no hacer muchas consultas gastando recursos.
    //@Query("select c from Client c join fetch c.addresses where c.id=?1") // c.addresses es el atributo addresses de la clase Client
    @Query("select c from Client c left join fetch c.addresses where c.id=?1")
    Optional<Client> findOneWithAddresses(Long id);


    //Buscar al cliente y sus facturas en una sola consulta. Para no hacer muchas consultas gastando recursos.
    //@Query("select c from Client c join fetch c.invoices where c.id=?1") // c.addresses es el atributo addresses de la clase Client
    @Query("select c from Client c left join fetch c.invoices where c.id=?1") 
    Optional<Client> findOneWithInvoices(Long id);

    //Consulta Trae a los clientes con sus facturas y direcciones. //Para mostrarlo esta escrito en el metodo toString de Client
   //@Query("select c from Client c left join fetch c.invoices left join fetch c.addresses where c.id=?1") 
   //Trae invoices, addresses, clientsDetails estos variables estan definidas en la clase Client
   @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id=?1")  
   Optional<Client> findOne(Long id);

}
