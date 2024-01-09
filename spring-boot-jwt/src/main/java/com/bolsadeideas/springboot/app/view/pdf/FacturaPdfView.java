package com.bolsadeideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Ejemplos: https://kb.itextpdf.com/itext/examples

@Component("factura/ver") //al redireccionar a la vista "factura/ver" del return del metodo ver de FacturaController muestra la vista en formato pdf 
public class FacturaPdfView extends AbstractPdfView{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		Factura factura = (Factura) model.get("factura"); //obtenemos los datos del objeto factura utilizando la variable factura que une al controlador y a vista eso usando model.
		
		//Forma 1 para la traduccion a idiomas usa: messageSource.getMessage("text.login.success", null, locale)
		Locale locale = localeResolver.resolveLocale(request); 
		
		//Forma 2 de traducir: Se traduciran los campos(encabezados e items) de las tablas 
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
			//Tabla 1 para el Cliente
			PdfPTable tabla = new PdfPTable(1);
			
			tabla.setSpacingAfter(20);
			
			PdfPCell cell = null;
			// cell = new PdfPCell(new Phrase("Datos del Cliente")); //Sin traduccion
			cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));  //Con traduccion
			
			cell.setBackgroundColor(new Color(184, 218, 255)); //Agregar color de fondo a la celda 
			cell.setPadding(8f);
			
			tabla.addCell(cell);
			tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
			tabla.addCell(factura.getCliente().getEmail());
			
			//Tabla 2 para la Factura
			PdfPTable tabla2 = new PdfPTable(1);
			tabla2.setSpacingAfter(20);
			
			//cell = new PdfPCell(new Phrase("Datos de la Factura")); //Sin traduccion
			cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale))); //Con traduccion
			cell.setBackgroundColor(new Color(195, 230, 203)); //Agregar color de fondo a la celda 
			cell.setPadding(8f);
			
			tabla2.addCell(cell); //Se agrega la variable cell que contiene el texto y el color de fondo
			
			//sin traduccion
		/*	tabla2.addCell("Folio: " + factura.getId());
			tabla2.addCell("Descripción: " + factura.getDescripcion());
			tabla2.addCell("Fecha: " + factura.getCreateAt());
		*/	
			//Con Traduccion
			tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
			tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
			tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
			
			//Guarda la tabla al documento
			document.add(tabla);
			document.add(tabla2);
			
			//Tabla 3 para los productos
			//Header de la tabla
			PdfPTable tabla3 = new PdfPTable(4);
			tabla3.setWidths(new float[] {3.5f, 1, 1, 1}); //la primera columna tendra 3.5f de espacio horizontal, la segunda 1, la 3ra. 1 y 4ta. 1
			
			//Sin traduccion
/*			tabla3.addCell("Producto");
			tabla3.addCell("Precio");
			tabla3.addCell("Cantidad");
			tabla3.addCell("Total");
*/			
			//Con traduccion
			tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
			tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
			tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
			tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));
			
			//Items de las tablas - Recibir los datos de los productos de las tablas
			for(ItemFactura item: factura.getItems()) {
				tabla3.addCell(item.getProducto().getNombre());
				tabla3.addCell(item.getProducto().getPrecio().toString());
				
				//Ejemplo de como centrar los datos de una columna
				cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); //centramos el contenido de la variable cell
				tabla3.addCell(cell);//enviamos a la tabla 3 el cell
				
				tabla3.addCell(item.calcularImporte().toString());
			}
			
			//Footer
			//cell = new PdfPCell(new Phrase("Total: "));
			cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total")));
			cell.setColspan(3); //espacio de 3
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);//alinear a la derecha
			tabla3.addCell(cell); //se agrego la celda a la tabla 3
			tabla3.addCell(factura.getTotal().toString());
			
			//Guarda la tabla al documento
			document.add(tabla3);
	}

}









