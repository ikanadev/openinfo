package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.informatica.openInfo.apirest.models.Equipo;

public interface IEquipoDao extends CrudRepository<Equipo, Long>{

	@Query(value="select distinct e.*\r\n" + 
			"from equipo_actividad eq,equipo e\r\n" + 
			"where eq.actividad_id_actividad IN (select id_actividad\r\n" + 
			"									from actividad  \r\n" + 
			"									where tipo_actividad_id = 1 or tipo_actividad_id = 2)\r\n" + 
			"and e.id_equipo = eq.equipo_id_equipo and e.gestion_id = ?1",nativeQuery=true)
	List<Equipo> listaEquiposTalksProyectos(Long idGestion);
	
	@Query(value="select distinct e.*\r\n" + 
			"from equipo_actividad eq,equipo e\r\n" + 
			"where eq.actividad_id_actividad IN (select id_actividad\r\n" + 
			"									from actividad  \r\n" + 
			"									where tipo_actividad_id = 3)\r\n" + 
			"and e.id_equipo = eq.equipo_id_equipo and e.gestion_id = ?1",nativeQuery=true)
	List<Equipo> listaEquiposConcursos(Long idGestion);
	
	List<Equipo> findByTipoEquipo_IdAndGestion_IdOrderByIdEquipoAsc(Long id,Long idGestion);
	
	List<Equipo> findByEncargado_codRegistroAndGestion_IdOrderByIdEquipoAsc(String codRegistro,Long idGestion);
	
	Equipo findByNombreAndGestion_IdOrderByIdEquipoAsc(String nombre,Long idGestion); 
}
