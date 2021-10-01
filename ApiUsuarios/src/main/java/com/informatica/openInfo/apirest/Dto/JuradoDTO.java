package com.informatica.openInfo.apirest.Dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JuradoDTO {

	private Long id;
	
	private String gradoAcademico;
	
	private String telefono;
	
	private Usuario usuario; 
	
	private List<JuradoProyectosDTO> proyectos;
}
