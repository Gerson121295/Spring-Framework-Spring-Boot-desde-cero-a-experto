package com.bolsadeideas.springboot.app.view.csv;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar.csv") //vinculado a la vista listar - ClienteController
public class ClienteCsvView extends AbstractView{
	
	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Asignar nombre al archivo Csv
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());
		
		//Con model traemos los datos de clientes
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		//Convertimos los datos de clientes en datos para el archivo CSV
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference .STANDARD_PREFERENCE);
		
		//Atributos a comvertir en el archivo plano csv
		String[] header = {"id", "nombre", "apellido", "email", "createAt"}; //Encabezado de la tabla
		beanWriter.writeHeader(header);//pasamos el encabezado de la tabla para su conversion
		
		//Agregamos el contenido o clientes a la tabla
		for(Cliente cliente: clientes) {
			beanWriter.write(cliente, header);
		}
		beanWriter.close(); //cerrar el recurso
	}

}




