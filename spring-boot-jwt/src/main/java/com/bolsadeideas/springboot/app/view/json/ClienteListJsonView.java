package com.bolsadeideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;

//Para exportar JSON no es necesario dependencias viene por defecto en spring 

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView{
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		
		//Eliminamos datos de la vista no es necesario el titulo y el numero de pagina
			model.remove("titulo");
			model.remove("page");
			
			//Eliminamos la paginacion del cliente porque no queremos que aparezca al exportar a JSON
			Page<Cliente> clientes = (Page<Cliente>) model.get("clientes"); //Obtenemos los clientes paginados
			model.remove(clientes); //eliminamos los clientes paginados 
			
			//Obtenemos los clientes sin paginacion y pasamos los datos para que sean convertidos a JSON
			model.put("clientes", clientes.getContent());
			
			return super.filterModel(model);
	}

}
