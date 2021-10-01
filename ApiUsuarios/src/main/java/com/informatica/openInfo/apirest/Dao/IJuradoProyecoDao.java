package com.informatica.openInfo.apirest.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.JuradoProyectos;

public interface IJuradoProyecoDao extends CrudRepository<JuradoProyectos, Long>{

	List<JuradoProyectos> findByProyecto_Area(String area);
	
	List<JuradoProyectos> findByProyecto_Id(Long id);
	
	Optional<JuradoProyectos> findByProyecto_IdAndJurado_Usuario_CodRegistro(Long ProyectoId,String codRegistro);
	
	List<JuradoProyectos> findByJurado_Id(Long id);
}
