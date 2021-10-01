package com.informatica.openInfo.apirest.services;

import java.util.List;

import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.TipoEquipo;

public interface IEquipoService {

	public List<Equipo> findAll();
	
	public Equipo findById(Long idEquipo);
	
	public Equipo save(Equipo equipo);
	
	public List<Equipo> listaEquiposTalksProyectos(Long idGestion);
	
	public List<Equipo> listaEquiposConcursos(Long idGestion);
	
	public List<TipoEquipo> listaTipoEquipo();
	
	public TipoEquipo findByTipoEquipo(Long idTipoEquipo);
	
	public List<Equipo> findByTipoEquipo_Id(Long id,Long idGestion);
	
	public List<Equipo> findByEncargado_codRegistro(String codRegistro,Long idGestion);
	
	public Equipo findByNombre(String nombre,Long idGestion);
	
}
