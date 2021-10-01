package com.informatica.openInfo.apirest.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.Jurado;

public interface IJuradoDao extends CrudRepository<Jurado, Long>{
	
	Optional<Jurado> findByUsuario_CodRegistro(String codRegistro);
	
	Optional<List<Jurado>> findByOrderByIdAsc();

}
