package com.bolsadeideas.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> { //T describe que es generico se podra paginar una clase de clientes, producto o otra clase.
	private String url;
	private Page<T> page;
	
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActual;
	private List <PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		numElementosPorPagina = page.getSize(); //page.getSize tiene el tamaño de pagina definido en el ClienteController
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1; //por default inicia en la 0 al sumarle 1 empezara en la pagina 1
		
		int desde, hasta;
		if(totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		}else {
			if(paginaActual <= numElementosPorPagina/2) {
				desde = 1;
				hasta = numElementosPorPagina;
			}else if(paginaActual >= totalPaginas - numElementosPorPagina/2) { //Rango final
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			}else { //Rango de en medio
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
				
			}
		}
		
		//Recorremos el for y empezamos a llenar las paginas
		for(int i=0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde+i));
		}
		
			
	}

	
	//Getters
	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	//Metodo para saber en que pagina esta: si tiene pagina siguiente, si tiene atras
	public boolean isFirst() { //Si es la primera pagina
		return page.isFirst();
	}
	
	public boolean isLast() { //Si es la ultima pagina
		return page.isLast();
	}
	
	public boolean isHasNext() { //Si tiene pagina siguiente
		return page.hasNext();
	}
	
	public boolean isHasPrevious(){//Si tiene pagina anterior
		return page.hasPrevious();
	}
	
}


