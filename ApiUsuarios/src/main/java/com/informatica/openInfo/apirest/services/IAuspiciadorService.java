package com.informatica.openInfo.apirest.services;

import java.util.List;

import com.informatica.openInfo.apirest.models.Auspiciador;

public interface IAuspiciadorService {
	
	public List<Auspiciador> findAll();
	
	public Auspiciador findById(Long id);
	
	public Auspiciador findByNombre(String nombre);
	
	public Auspiciador save(Auspiciador auspiciador);
	
	public void delete(Long id);

}
