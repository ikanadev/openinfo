package com.informatica.openInfo.apirest.services;

import java.util.List;

import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;

public interface IUsuarioRolService {
	
	public List<UsuarioRol> findAll();
	
	public UsuarioRol buscarPorCodRegistroAndRol(String codRegistro, Long idRol);
	
	public UsuarioRol findById(UsuarioRolKey usuarioRolkey);
	
	public List<UsuarioRol> buscarPorRol(Long idRol);
	
	public UsuarioRol save(UsuarioRol rol);
	
	public void delete(UsuarioRol usuarioRol);

}
