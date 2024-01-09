package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IClienteService {
	
	//Metodos de IClienteDao
	public List<Cliente> findAll(); //Metodo listar todos los clientes.

	public Page<Cliente> findAll(Pageable pageable); //Paginación -Metodo listar todos los clientes.
	
	public void save(Cliente cliente); //Guardar un cliente.
	
	public Cliente findOne(Long id);//Metodo buscar por id.
	
	public void delete(Long id);
	
	//Metodo de IProductoDao
	
	//Forma 1 de hacer la consulta JPA para buscar productos 
	public List<Producto> findByNombre(String term);
	
	//Forma 2 de hacer la consulta JPA para buscar productos 
	public List<Producto> findByNombreLikeIgnoreCase(String term);
	
	public Producto findProductoById(Long id); //Encontrar producto por id
	
	//Metodo de IFacturaDao
	public void saveFactura(Factura factura);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	//Optimizando consultas JPQL en Facturas JOIN FETCH para obtener los items
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
	
	//Optimizando consultas JPQL en Clientes con JOIN FETCH para obtener las facturas.
	public Cliente fetchByIdWithFacturas(Long id);
	
}



