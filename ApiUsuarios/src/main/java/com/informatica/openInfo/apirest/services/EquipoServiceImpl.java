package com.informatica.openInfo.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IEquipoDao;
import com.informatica.openInfo.apirest.Dao.ITipoEquipoDao;
import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.TipoEquipo;

@Service
public class EquipoServiceImpl implements IEquipoService{
	
	@Autowired
	private IEquipoDao equipoDao;
	
	@Autowired
	private ITipoEquipoDao tipoEquipoDao;

	@Override
	public List<Equipo> findAll() {
		// TODO Auto-generated method stub
		return (List<Equipo>) equipoDao.findAll();
	}

	@Override
	public Equipo findById(Long idEquipo) {
		// TODO Auto-generated method stub
		return equipoDao.findById(idEquipo).orElse(null);
	}

	@Override
	public Equipo save(Equipo actividad) {
		// TODO Auto-generated method stub
		return equipoDao.save(actividad);
	}


	@Override
	public List<Equipo> listaEquiposTalksProyectos(Long idGestion) {
		// TODO Auto-generated method stub
		return equipoDao.listaEquiposTalksProyectos(idGestion);
	}

	@Override
	public List<Equipo> listaEquiposConcursos(Long idGestion) {
		// TODO Auto-generated method stub
		return equipoDao.listaEquiposConcursos(idGestion);
	}

	@Override
	public List<TipoEquipo> listaTipoEquipo() {
		// TODO Auto-generated method stub
		return (List<TipoEquipo>) tipoEquipoDao.findAll();
	}

	@Override
	public TipoEquipo findByTipoEquipo(Long idTipoEquipo) {
		// TODO Auto-generated method stub
		return tipoEquipoDao.findById(idTipoEquipo).orElse(null);
	}

	@Override
	public List<Equipo> findByTipoEquipo_Id(Long id,Long idGestion) {
		// TODO Auto-generated method stub
		return equipoDao.findByTipoEquipo_IdAndGestion_IdOrderByIdEquipoAsc(id,idGestion);
	}

	@Override
	public List<Equipo> findByEncargado_codRegistro(String codRegistro,Long idGestion) {
		// TODO Auto-generated method stub
		return equipoDao.findByEncargado_codRegistroAndGestion_IdOrderByIdEquipoAsc(codRegistro,idGestion);
	}

	@Override
	public Equipo findByNombre(String nombre,Long idGestion) {
		// TODO Auto-generated method stub
		return equipoDao.findByNombreAndGestion_IdOrderByIdEquipoAsc(nombre,idGestion);
	}

}
