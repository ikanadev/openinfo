package com.informatica.openInfo.apirest.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.informatica.openInfo.apirest.models.Participante;

public interface IParticipanteDao extends CrudRepository<Participante, Long>{
	
	
	@Query(value="select *\r\n" + 
			"from participante\r\n" + 
			"where id_participante IN (select participante_id_participante id_participante\r\n" + 
			"						    from participante_actividad)",nativeQuery=true)
	List<Participante> listaDeParticipantes();

	Participante findByUsuario_Nombre(String nombre);
	
	Optional<Participante> findByUsuario_CodRegistro(String codRegistro);
	
	@Query(value="select p.* from participante p, participante_actividad pa \r\n" + 
			"where p.id_participante = pa.participante_id_participante and pa.actividad_id_actividad = ?1",nativeQuery=true)
	List<Participante> listarPorActividad(Long idActividad);
	
	@Query(value="select p.*\r\n" + 
			"from participante p, participante_proyecto py, proyecto pr\r\n" + 
			"where p.id = py.participante_id and py.proyecto_id = pr.id and pr.equipo_id_equipo = ?1", nativeQuery=true)
	List<Participante> listarPorEquipo(Long idEquipo);
	
}
