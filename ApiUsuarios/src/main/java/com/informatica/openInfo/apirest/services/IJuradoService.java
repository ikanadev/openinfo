package com.informatica.openInfo.apirest.services;

import java.util.List;
import java.util.Optional;

import com.informatica.openInfo.apirest.models.Jurado;
import com.informatica.openInfo.apirest.models.JuradoProyectos;

public interface IJuradoService {
	
	public List<Jurado> findAll();
	
	public Jurado findById(Long codRegistro);
	
	public Jurado findByCodRegistro(String codRegistro);
	
	public Jurado save(Jurado jurado);
	
	public List<JuradoProyectos> findByProyecto_area(String area);
	
	public JuradoProyectos findByIdJuradoProyectos(Long id);
	
	public List<JuradoProyectos> findByProyecto_Id(Long id);
	
	public List<JuradoProyectos> findByJurado_Id(Long id);
	
	public JuradoProyectos registrarJurado(JuradoProyectos juradoProyectos);
	
	public JuradoProyectos findByProyecto_IdAndJurado_Usuario_CodRegistro(Long ProyectoId,String codRegistro);
	

}
