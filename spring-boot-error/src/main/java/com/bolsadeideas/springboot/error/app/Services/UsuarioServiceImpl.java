package com.bolsadeideas.springboot.error.app.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> lista;
	
	
	public UsuarioServiceImpl() {
		this.lista = new ArrayList<>();
		this.lista.add(new Usuario(1, "Gerson", "Ep"));
		this.lista.add(new Usuario(2, "Pepa", "Guzman"));
		this.lista.add(new Usuario(3, "lalo", "Yas"));
		this.lista.add(new Usuario(4, "Jasibe", "Xert"));
		this.lista.add(new Usuario(5, "Iñiho", "As"));
		this.lista.add(new Usuario(6, "Dana", "Rte"));
		this.lista.add(new Usuario(7, "Paola", "Esc"));
		
	}

	
	@Override
	public List<Usuario> listar() {
		
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null; //asignamos un Usuario llamado resultado el cual definimos como null
		
		for(Usuario u: this.lista) { //Recorremos la lista 
			if(u.getId().equals(id)) { //Si el id del usuario es igual, o podria ser: if(u.getId() == id)
				resultado = u;
				break;
			}
		}
		
		return resultado;
	}


	//Lanzamiento de exepcion usando API optiona de java 8: Busca por id usando Optional de java 8
	@Override
	public Optional<Usuario> obtenerPorIdOptional(Integer id) {
		 Usuario usuario= this.obtenerPorId(id);
		return Optional.ofNullable(usuario);
	}
	

}



