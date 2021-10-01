package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.ParticipanteProyecto;

public interface IParticipanteProyectoDao extends CrudRepository<ParticipanteProyecto, Long>{

	@Query(value="select *\r\n" + 
			"from participante_proyecto\r\n" + 
			"where proyecto_id = ?1", nativeQuery=true)
	List<ParticipanteProyecto> listaParticipantes(Long idActividad);
	
	@Query(value="select pa.* \r\n" + 
			"from participante_actividad pa, actividad a \r\n" + 
			"where a.tipo_actividad_id = 2 and a.id_actividad = pa.actividad_id_actividad", nativeQuery=true)
	List<ParticipanteProyecto> listarProyectos();
	
	@Query(value="select pa.* \r\n" + 
			"from participante_actividad pa, actividad a \r\n" + 
			"where a.tipo_actividad_id = 3 and a.id_actividad = pa.actividad_id_actividad", nativeQuery=true)
	List<ParticipanteProyecto> listarConcursos();
	
	List<ParticipanteProyecto> findByParticipante_Usuario_CodRegistro(String codRegistro);
	
	List<ParticipanteProyecto> findByParticipante_Usuario_CodRegistroAndRepresentanteTrueAndProyecto_Gestion_Id(String codRegistro,Long gestionId);
	
	ParticipanteProyecto findByRepresentanteTrueAndProyecto_Id(Long id);
	
	ParticipanteProyecto findByProyecto_Id(Long id);
}
