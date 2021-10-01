package com.informatica.openInfo.apirest.Dao;

import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.Auspiciador;

public interface IAuspiciadorDao extends CrudRepository<Auspiciador, Long>{
	
	Auspiciador findByNombre(String nombre);

}
