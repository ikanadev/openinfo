package com.informatica.openInfo.apirest.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.MiniTalk;

public interface IMiniTalkDao extends CrudRepository<MiniTalk, Long>{

		
	List<MiniTalk> findByHabilitadoTrueAndGestion_IdOrderByVistasDesc(Long idGestion);
	
	List<MiniTalk> findByGestion_IdOrderByIdAsc(Long idGestion);
	
	MiniTalk findByNombre(String nombre);
	
	MiniTalk findByCodigo(String codigo);
	
	@Query(value="select count(*) from mini_talk",nativeQuery=true)
	String cantidadDeRegistros();
	
	List<MiniTalk> findTop10ByHabilitadoTrueAndGestion_IdOrderByVistasDesc(Long gestionId);
}
