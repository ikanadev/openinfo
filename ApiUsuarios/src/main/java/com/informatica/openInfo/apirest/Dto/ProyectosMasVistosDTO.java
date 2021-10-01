package com.informatica.openInfo.apirest.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProyectosMasVistosDTO {

	private Long idProyecto;
	
	private String nombre;
	
	private String linkOficial;
	
	private Long vistas;
}
