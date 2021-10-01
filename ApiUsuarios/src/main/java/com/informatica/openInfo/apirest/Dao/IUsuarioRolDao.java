package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;

public interface IUsuarioRolDao extends CrudRepository<UsuarioRol, UsuarioRolKey>{
	
	UsuarioRol findByUsuario_CodRegistroAndRol_IdRol(String idUsuario, Long idRol);
	
	List<UsuarioRol> findByRol_IdRolOrderByUsuario_CodRegistroAsc(Long idRol);

	
}
