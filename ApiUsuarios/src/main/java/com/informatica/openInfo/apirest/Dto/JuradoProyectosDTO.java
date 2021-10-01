package com.informatica.openInfo.apirest.Dto;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import com.informatica.openInfo.apirest.models.Jurado;
import com.informatica.openInfo.apirest.models.Proyecto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JuradoProyectosDTO {
	
	private Long id;
		
	private Proyecto proyecto;
	
	private Double innovacion;
	
	private Double impacto;
	
	private Double funcionalidad;
	
	private Double ux;
	
	private Double presentacion;

}
