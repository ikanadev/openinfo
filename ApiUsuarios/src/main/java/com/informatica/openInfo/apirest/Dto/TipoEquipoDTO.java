package com.informatica.openInfo.apirest.Dto;

import java.util.List;

import com.informatica.openInfo.apirest.models.Equipo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoEquipoDTO {
	
	private Long id;
	
	private String nombre;
	
	private List<EquipoDTO> equipos;

}
