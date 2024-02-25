package com.gerson.curso.springboot.jpa.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.gerson.curso.springboot.jpa.springbootjparelationship.entities.Invoice;

//No se anota la clase porque cuando se extiende de CrudRepository ya es una clase de tipo component
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

}

