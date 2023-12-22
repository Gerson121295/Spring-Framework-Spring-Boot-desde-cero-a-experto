package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.app.models.entity.Factura;

public interface IFacturaDao extends JpaRepository<Factura, Long> {

	//Optimizando consultas JPQL en Facturas JOIN FETCH para obtener los items: Solo hace 1 consulta, al no optimizarlo realiza 7 consultas por lo que afecta el rendimiento
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1") //f es el alias de Factura, c es el alias de: f.cliente, l es el alias de f.items, where f.id=?1 donde el id de factura sea 1 "el primero" (luego el segundo y asi) 
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id); //recibe el id de la factura
	
}

