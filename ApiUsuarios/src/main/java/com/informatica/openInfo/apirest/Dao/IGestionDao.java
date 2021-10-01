package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.Gestion;

public interface IGestionDao extends CrudRepository<Gestion, Long>{

	@Query(value="select * from gestion\r\n"
			+ "ORDER BY id desc\r\n"
			+ "limit 1", nativeQuery = true )
	Gestion ultimaGestion();
	
	Gestion findByHabilitadoTrue();
	
	List<Gestion> findByOrderByIdAsc();
	
	List<Gestion> findByEstadoTrueOrderByIdAsc();
	
}
