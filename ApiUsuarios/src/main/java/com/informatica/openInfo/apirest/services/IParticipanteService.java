package com.informatica.openInfo.apirest.services;

import java.util.List;
import java.util.Optional;

import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.Participante;

public interface IParticipanteService {
	
	public List<Participante> findAll();
	
	public List<Participante> listaParticipantes();
	
	public List<Participante> listarPorActividad(Long idActividad);
	
	public Participante findById(Long idParticipante);
	
	public Participante save(Participante participante);
	
	public void delete(Long idParticipante);
	
	public List<Participante> listarPorEquipo(Long idEquipo);
	
	public Participante findByNombre(String nombre);
	
	public Participante findByUsuario_CodRegistro(String codRegistro);
	
}
