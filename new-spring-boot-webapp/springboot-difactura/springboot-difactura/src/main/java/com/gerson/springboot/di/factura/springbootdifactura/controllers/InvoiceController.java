package com.gerson.springboot.di.factura.springbootdifactura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerson.springboot.di.factura.springbootdifactura.models.Client;
import com.gerson.springboot.di.factura.springbootdifactura.models.Invoice;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private Invoice invoice;

    @GetMapping("/show")  // http://localhost:8080/invoices/show
    public Invoice show(){
        //return invoice; 

        //Otra forma de retornar, para evitar usar en la clase Invoice y Client la anotacion: @JsonIgnoreProperties({"targetSource", "advisors"}) para resolver errores 
        //Esto para evitar el jSON proxy que retorna al solo retornar invoice;
        Invoice i = new Invoice();
        Client c = new Client();

        c.setLastname(invoice.getClient().getLastname());
        c.setName(invoice.getClient().getName());

        i.setClient(c);
        i.setDescription(invoice.getDescription());
        i.setItems(invoice.getItems());

        return i;
    }

}
