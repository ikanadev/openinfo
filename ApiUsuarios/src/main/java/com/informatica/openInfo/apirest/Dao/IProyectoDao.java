package com.informatica.openInfo.apirest.Dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Usuario;

public interface IProyectoDao extends CrudRepository<Proyecto, Long>{
	
	List<Proyecto> findByHabilitadoTrueAndGestion_IdAndAreaLikeOrderByVistasDesc(Long idGestion,String area);
	
	List<Proyecto> findTop10ByHabilitadoTrueAndGestion_IdAndAreaLikeOrderByVistasDesc(Long gestionId,String area);
	
	List<Proyecto> findByGestion_IdAndAreaLikeOrderByIdAsc(Long gestionId,String area);
	
	List<Proyecto> findByNombreContainingIgnoreCaseAndGestion_Id(String nomb,Long gestionId);
	
	Optional<List<Proyecto>> findByTipoProyecto_IdAndAreaAndGestion_Id(Long tipoProyecto,String area,Long gestionId);
	
	@Query(value="select count(*) from proyecto",nativeQuery=true)
	String cantidadDeRegistros();
	
	Optional<Proyecto> findByCodigo(String Codigo);
	
	List<Proyecto> findByEquipo_Encargado_CodRegistroAndGestion_Id(String codRegistro,Long gestionId);
	
}
