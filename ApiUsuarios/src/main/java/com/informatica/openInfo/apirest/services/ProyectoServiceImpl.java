package com.informatica.openInfo.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IProyectoDao;
import com.informatica.openInfo.apirest.Dao.IGestionDao;
import com.informatica.openInfo.apirest.Dao.IMiniTalkDao;
import com.informatica.openInfo.apirest.Dao.IParticipanteProyectoDao;
import com.informatica.openInfo.apirest.Dao.ITipoProyectoDao;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.ParticipanteProyecto;
import com.informatica.openInfo.apirest.models.TipoProyecto;

@Service
public class ProyectoServiceImpl implements IProyectoService{

	@Autowired
	private IProyectoDao proyectoDao;
	
	@Autowired
	private IParticipanteProyectoDao participanteActividadDao;
	
	@Autowired
	private ITipoProyectoDao tipoProyectoDao;
	
	@Autowired
	private IMiniTalkDao miniTalDao;
	
	@Autowired
	private IGestionDao gestionDao;
	
	@Override
	public List<Proyecto> findAll() {
		// TODO Auto-generated method stub
		return (List<Proyecto>) proyectoDao.findAll();
	}

	@Override
	public Proyecto findById(Long idActividad) {
		// TODO Auto-generated method stub
		return proyectoDao.findById(idActividad).orElse(null);
	}

	@Override
	public Proyecto save(Proyecto actividad) {
		// TODO Auto-generated method stub
		return proyectoDao.save(actividad);
	}

	@Override
	public void delete(Long idActividad) {
		// TODO Auto-generated method stub
		proyectoDao.deleteById(idActividad);
	}

	@Override
	public List<ParticipanteProyecto> listaParticipantes(Long idActividad) {
		// TODO Auto-generated method stub
		return participanteActividadDao.listaParticipantes(idActividad);
	}

	@Override
	public List<ParticipanteProyecto> listaProyectos() {
		// TODO Auto-generated method stub
		return participanteActividadDao.listarProyectos();
	}

	@Override
	public List<ParticipanteProyecto> listaConcurso() {
		// TODO Auto-generated method stub
		return participanteActividadDao.listarConcursos();
	}

	@Override
	public List<TipoProyecto> listaTipoProyectos() {
		// TODO Auto-generated method stub
		return (List<TipoProyecto>) tipoProyectoDao.findAll();
	}

	@Override
	public List<MiniTalk> miniTalks(Long gestionId) {
		// TODO Auto-generated method stub
		return (List<MiniTalk>) miniTalDao.findByHabilitadoTrueAndGestion_IdOrderByVistasDesc(gestionId);
	}

	@Override
	public List<Proyecto> findByAreaLike(Long idGestion,String area) {
		// TODO Auto-generated method stub
		return proyectoDao.findByHabilitadoTrueAndGestion_IdAndAreaLikeOrderByVistasDesc(idGestion, area);
	}

	@Override
	public TipoProyecto findByIdTipo(Long id) {
		// TODO Auto-generated method stub
		return tipoProyectoDao.findById(id).orElse(null);
	}

	@Override
	public ParticipanteProyecto nuevoParticipanteProyecto(ParticipanteProyecto participanteProyecto) {
		// TODO Auto-generated method stub
		return participanteActividadDao.save(participanteProyecto);
	}

	@Override
	public List<ParticipanteProyecto> findByParticipante_Usuario_CodRegistro(String codRegistro) {
		// TODO Auto-generated method stub
		return participanteActividadDao.findByParticipante_Usuario_CodRegistro(codRegistro);
	}

	@Override
	public MiniTalk saveTalk(MiniTalk miniTalk) {
		// TODO Auto-generated method stub
		return miniTalDao.save(miniTalk);
	}

	@Override
	public MiniTalk findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return miniTalDao.findByNombre(nombre);
	}

	@Override
	public List<Proyecto> findByNombreContainingAndGestion(String nomb,Long gestionId) {
		// TODO Auto-generated method stub
		return proyectoDao.findByNombreContainingIgnoreCaseAndGestion_Id(nomb,gestionId);
	}

	@Override
	public ParticipanteProyecto findByRepresentanteTrueAndProyecto_Id(Long id) {
		// TODO Auto-generated method stub
		return participanteActividadDao.findByRepresentanteTrueAndProyecto_Id(id);
	}

	@Override
	public List<MiniTalk> topTalks(Long gestionId) {
		// TODO Auto-generated method stub
		return miniTalDao.findTop10ByHabilitadoTrueAndGestion_IdOrderByVistasDesc(gestionId);
	}

	@Override
	public List<Proyecto> topProyectos(Long gestionId, String area) {
		// TODO Auto-generated method stub
		return proyectoDao.findTop10ByHabilitadoTrueAndGestion_IdAndAreaLikeOrderByVistasDesc(gestionId, area);
	}

	@Override
	public TipoProyecto findByTipoProyectoID(Long id) {
		// TODO Auto-generated method stub
		return tipoProyectoDao.findById(id).orElse(null);
	}

	@Override
	public List<ParticipanteProyecto> proyectosJefeDeActividad(String codRegistro,Long gestionId) {
		// TODO Auto-generated method stub
		return participanteActividadDao.findByParticipante_Usuario_CodRegistroAndRepresentanteTrueAndProyecto_Gestion_Id(codRegistro,gestionId);
	}

	@Override
	public List<Proyecto> proyectosDocente(String codRegistro,Long gestionId) {
		// TODO Auto-generated method stub
		return proyectoDao.findByEquipo_Encargado_CodRegistroAndGestion_Id(codRegistro,gestionId);
	}

	@Override
	public Gestion ultimaGestion() {
		// TODO Auto-generated method stub
		return gestionDao.ultimaGestion();
	}

	@Override
	public List<MiniTalk> listaTalks(Long idGestion) {
		// TODO Auto-generated method stub
		return miniTalDao.findByGestion_IdOrderByIdAsc(idGestion);
	}

	@Override
	public List<Proyecto> listaDeProyectos(Long gestionId, String area) {
		// TODO Auto-generated method stub
		return proyectoDao.findByGestion_IdAndAreaLikeOrderByIdAsc(gestionId, area);
	}

	@Override
	public MiniTalk findByIdTalk(Long id) {
		// TODO Auto-generated method stub
		return miniTalDao.findById(id).orElse(null);
	}

	@Override
	public List<Proyecto> findByTipoProyecto_IdAndArea(Long tipoProyecto, String area,Long gestionId) {
		// TODO Auto-generated method stub
		return proyectoDao.findByTipoProyecto_IdAndAreaAndGestion_Id(tipoProyecto, area,gestionId).orElse(null);
	}

	@Override
	public Gestion registrarGestion(Gestion gestion) {
		// TODO Auto-generated method stub
		return gestionDao.save(gestion);
	}

	@Override
	public List<Gestion> listarGestiones() {
		// TODO Auto-generated method stub
		return (List<Gestion>) gestionDao.findByEstadoTrueOrderByIdAsc();
	}

	@Override
	public Gestion buscarGestionPorId(Long id) {
		// TODO Auto-generated method stub
		return gestionDao.findById(id).orElse(null);
	}

	@Override
	public Gestion findByHabilitadoTrue() {
		// TODO Auto-generated method stub
		return gestionDao.findByHabilitadoTrue();
	}

	@Override
	public List<Gestion> allGestiones() {
		// TODO Auto-generated method stub
		return gestionDao.findByOrderByIdAsc();
	}

}
