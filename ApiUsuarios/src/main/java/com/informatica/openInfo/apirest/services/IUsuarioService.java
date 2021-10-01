package com.informatica.openInfo.apirest.services;

import java.util.List;

import com.informatica.openInfo.apirest.models.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Usuario findById(String codRegistro);
	
	public List<Usuario> buscarPorNombre(String nombre);
	
	public List<Usuario> buscarPorCodRegistro(String codRegistro);
	
	public Usuario findByCorreo(String correo);
	
	public Usuario save(Usuario usuario);
	
	public void delete(String id);
	
	public String cantidadDeRegistros();
	
	public Usuario findByNombre(String nombre);
	
}
