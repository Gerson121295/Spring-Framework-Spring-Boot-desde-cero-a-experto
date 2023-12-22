package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

	//Forma con version menor a 3 de Spring Boot
	//Creacion de DAO usando CrudRepository Forma 2: Al extender de CrudRepository ya trae metodos (Precionar Ctrl + clic en CrudRepository para ver los metodos)

//public interface IClienteDao extends CrudRepository<Cliente, Long>{} //Se agrega la clase a usar "Cliente", y el tipo de clave primaria "Long" 
	


/*
Actualización: para Spring Boot 3 o superior para PagingAndSortingRepository
Tengan en cuenta para los siguientes videos que en Spring Boot 3 la interface PagingAndSortingRepository ya no 
extiende de CrudRepository sino que extiende de Repository y no incluye los métodos del Crud.

Tenemos 2 soluciones:
 */


public interface IClienteDao extends JpaRepository<Cliente, Long>{
	
	//Optimizando consultas JPQL en Clientes con JOIN FETCH para obtener las facturas.  - Este metodo realiza 1 consulta, sin este metodo el anterior realiza 2 consultas.
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1") //@uery permite crear una consulta JPA optimizada
	//join el cliente debe tener factura de lo contrario aparecera que no existe en la BD
	//Agregar left join el cliente no es necesario que tenga facturas
	public Cliente fetchByIdWithFacturas(Long id);
}



/*
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>, CrudRepository<Cliente, Long>{
}
*/




