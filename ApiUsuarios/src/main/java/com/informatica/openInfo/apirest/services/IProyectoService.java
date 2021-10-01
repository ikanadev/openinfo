package com.informatica.openInfo.apirest.services;

import java.util.List;

import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.ParticipanteProyecto;
import com.informatica.openInfo.apirest.models.TipoProyecto;

public interface IProyectoService {

	public List<Proyecto> findAll();
	
	public List<ParticipanteProyecto> listaParticipantes(Long idActividad);
	
	public List<ParticipanteProyecto> listaProyectos();
	
	public List<ParticipanteProyecto> listaConcurso();
	
	public ParticipanteProyecto nuevoParticipanteProyecto(ParticipanteProyecto participanteProyecto);
	
	public Proyecto findById(Long idActividad);
	
	public Proyecto save(Proyecto actividad);
	
	public void delete(Long idActividad);
	
	public List<TipoProyecto> listaTipoProyectos();
	
	public TipoProyecto findByTipoProyectoID(Long id);
	
	public List<MiniTalk> miniTalks(Long gestionId);
	
	public List<Proyecto> findByAreaLike(Long idGestion,String area);
	
	public TipoProyecto findByIdTipo(Long id);
	
	public List<ParticipanteProyecto> findByParticipante_Usuario_CodRegistro(String codRegistro);
	
	public MiniTalk saveTalk(MiniTalk miniTalk);
	
	public MiniTalk findByNombre(String nombre);
	
	public MiniTalk findByIdTalk(Long id);
	
	public List<Proyecto> findByNombreContainingAndGestion(String nomb,Long gestionId);
	
	public List<Proyecto> proyectosDocente(String codRegistro,Long gestionId);
	
	public ParticipanteProyecto findByRepresentanteTrueAndProyecto_Id(Long id);
	
	public List<ParticipanteProyecto> proyectosJefeDeActividad(String codRegistro,Long gestionId);
	
	public List<MiniTalk> topTalks(Long gestionId);
	
	public List<Proyecto> topProyectos(Long gestionId, String area);

	public List<MiniTalk> listaTalks(Long idGestion);
	
	public List<Proyecto> listaDeProyectos(Long gestionId,String area);
	
	public List<Proyecto> findByTipoProyecto_IdAndArea(Long tipoProyecto,String area,Long gestionId);
	
	//servicios de gestiones
	
	public Gestion ultimaGestion();
	
	public Gestion registrarGestion(Gestion gestion);
	
	public List<Gestion> listarGestiones();
	
	public Gestion findByHabilitadoTrue();
	
	public Gestion buscarGestionPorId(Long id);
	
	public List<Gestion> allGestiones();
	
	
}
