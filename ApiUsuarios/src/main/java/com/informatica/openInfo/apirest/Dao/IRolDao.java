package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.Rol;

public interface IRolDao extends CrudRepository<Rol, Long>{

	@Query(value="select r.* from usuario_rol u, rol r where usuario_cod_registro like ?1 and u.rol_id_rol = r.id_rol", nativeQuery = true)
	List<Rol> rolesDeUsuario(String username);
	
}
