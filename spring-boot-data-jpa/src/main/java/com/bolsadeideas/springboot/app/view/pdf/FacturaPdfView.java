package com.bolsadeideas.springboot.app.view.pdf;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver") //al redireccionar a la vista "factura/ver" del return del metodo ver de FacturaController muestra la vista en formato pdf 
public class FacturaPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		Factura factura = (Factura) model.get("factura"); //obtenemos los datos del objeto factura utilizando la variable factura que une al controlador y a vista eso usando model.
			
			PdfPTable tabla = new PdfPTable(1);
			//Tabla 1 para el Cliente
			tabla.setSpacingAfter(20);
			tabla.addCell("Datos del Cliente");
			tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
			tabla.addCell(factura.getCliente().getEmail());
			
			//Tabla 2 para la Factura
			PdfPTable tabla2 = new PdfPTable(1);
			tabla2.setSpacingAfter(20);
			tabla2.addCell("Datos de la Factura");
			tabla2.addCell("Folio: " + factura.getId());
			tabla2.addCell("Descripción: " + factura.getDescripcion());
			tabla2.addCell("Fecha: " + factura.getCreateAt());
			
			//Guarda la tabla al documento
			document.add(tabla);
			document.add(tabla2);
			
			//Tabla 3 para los productos
			//Header de la tabla
			PdfPTable tabla3 = new PdfPTable(4);
			tabla3.addCell("Producto");
			tabla3.addCell("Precio");
			tabla3.addCell("Cantidad");
			tabla3.addCell("Total");
			
			//Items de las tablas - Recibir los datos de los productos de las tablas
			for(ItemFactura item: factura.getItems()) {
				tabla3.addCell(item.getProducto().getNombre());
				tabla3.addCell(item.getProducto().getPrecio().toString());
				tabla3.addCell(item.getCantidad().toString());
				tabla3.addCell(item.calcularImporte().toString());
			}
			
			//Footer
			PdfPCell cell = new PdfPCell(new Phrase("Total: "));
			cell.setColspan(3); //espacio de 3
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);//alinear a la derecha
			tabla3.addCell(cell); //se agrego la celda a la tabla 3
			tabla3.addCell(factura.getTotal().toString());
			
			//Guarda la tabla al documento
			document.add(tabla3);
	}

}









