package com.bolsadeideas.springboot.app.view.xml;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar.xml") //listar el listado de clientes (ClienteController metodo listar)
public class ClienteListXmlView extends MarshallingView{

	//Injectar el bean Jaxb2Marshaller que realiza la conversion de la entity a xml
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Eliminamos datos de la vista no es necesario el titulo y el numero de pagina
		model.remove("titulo");
		model.remove("page");
		
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes"); //Obtenemos los clientes paginados
		model.remove(clientes); //eliminamos los clientes paginados 
		
		//Obtenemos los clientes y se los pasamos a la clase wrapper ClienteList que se encargara de convertir los datos de la entity a XML
		model.put("clienteList", new ClienteList(clientes.getContent())); //clienteList es de la clase que convierte el entity a XML
		
		super.renderMergedOutputModel(model, request, response);
	}





}
