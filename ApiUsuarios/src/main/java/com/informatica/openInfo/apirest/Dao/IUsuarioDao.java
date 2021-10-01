package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.informatica.openInfo.apirest.models.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, String>{

	Usuario findByCorreo(String correo);
	
	List<Usuario> findByNombreContainingIgnoreCase(String nomb);
	
	List<Usuario> findByCodRegistroContainingIgnoreCase(String nomb);
	
	@Query(value="select  count(*) from usuario",nativeQuery=true)
	String cantidadDeRegistros();
	
	Usuario findByNombre(String nombre);
}
