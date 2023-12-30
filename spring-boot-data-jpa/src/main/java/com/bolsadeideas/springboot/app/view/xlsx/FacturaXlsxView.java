package com.bolsadeideas.springboot.app.view.xlsx;

import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Forma 1 para la traduccion a idiomas usa: messageSource.getMessage("text.login.success", null, locale)
		Locale locale = localeResolver.resolveLocale(request); 
				
		//Forma 2 de traducir: Se traduciran los campos(encabezados e items) de las tablas 
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		//Cambiar el nombre del archivo
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		
		Factura factura = (Factura) model.get("factura");//Obtenemos con model.get la variable factura los datos que se envia del FacturaController a la vista.
		
		Sheet sheet = workbook.createSheet("Factura Spring"); //Creamos pagina de excel
		
		//Forma 1 de crear filas y celdas
		Row row = sheet.createRow(0); //0 indica la primera fila
		Cell cell = row.createCell(0); // 0 primera celda
		
		//cell.setCellValue("Datos del Cliente"); //Sin Traduccion
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente")); //Con Traduccion
		row = sheet.createRow(1); //Crea la segunda fila
		cell = row.createCell(0); //primera celda de la segunda fila
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2); //Crea la tercera fila
		cell = row.createCell(0); //primera celda de la tercera fila	
		cell.setCellValue(factura.getCliente().getEmail());
	
		//Forma 2 de crear filas y celdas y asignarles valor
/* 		//Sin traduccion
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura"); //Crea la cuarta fila, crea la primera celda y en la primera celda agrega un titulo
		sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt());	 
*/
	
		//Con traduccion
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura")); 
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
			
		
		//Configuracion de estilos a las tablas
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		
		//fila 9 
		Row header = sheet.createRow(9); 
		//Sin Traduccion
/*		header.createCell(0).setCellValue("Producto");
		header.createCell(1).setCellValue("Precio");
		header.createCell(2).setCellValue("Cantidad");
		header.createCell(3).setCellValue("Total");
*/
		//Con traduccion a otro idioma
		header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));
		
		//Agregando estilo de borde a las filas o celdas de las tablas
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		//Agregando los campos o items de la factura
		
		int rownum = 10; //Contador inicia de la fila 10 porque las anteriores ya estan ocupadas
		
		
		for(ItemFactura item: factura.getItems()) {			
			Row fila = sheet.createRow(rownum ++); //rownum ++ incrementa
			
			
			cell = fila.createCell(0); //se crea la celda 0
			cell.setCellValue(item.getProducto().getNombre()); //asignamos valor a la celda
			cell.setCellStyle(tbodyStyle); //Se agrega estilo a la celda 0
			
			cell = fila.createCell(1); //se crea la celda 0
			cell.setCellValue(item.getProducto().getPrecio()); //asignamos valor a la celda
			cell.setCellStyle(tbodyStyle); //Se agrega estilo de borde a la celda
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle); //Se agrega estilo de borde a la celda
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle); //Se agrega estilo de borde a la celda
			
		}
		
		Row filaTotal = sheet.createRow(rownum);
		cell = filaTotal.createCell(2);
		//cell.setCellValue("Gran Total: ");
		cell.setCellValue(mensajes.getMessage("text.factura.form.total"));
		cell.setCellStyle(tbodyStyle); //Se agrega estilo de borde a la celda
		
		cell = filaTotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
	}
	

}












