package com.informatica.openInfo.apirest.Dto;

import java.util.List;

import com.informatica.openInfo.apirest.models.TipoEquipo;
import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipoDTO {
	
	private Long idEquipo;

	private String nombre;
	
	private Usuario encargado;
	
	private List<ParticipanteDTO> participantes;
	
}
