package com.informatica.openInfo.apirest.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarNotaDTO {

	private String codRegistro;
	
	private Long idProyecto;
	
	private Double innovacion;
	
	private Double impacto;
	
	private Double funcionalidad;
	
	private Double ux;
	
	private Double presentacion; 
}
