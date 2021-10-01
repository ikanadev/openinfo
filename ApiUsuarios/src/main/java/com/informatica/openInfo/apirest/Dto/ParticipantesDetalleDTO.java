package com.informatica.openInfo.apirest.Dto;

import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantesDetalleDTO {

	private Usuario usuario;
	
	private String ci;
	
	private String foto;
	
	private String gradoAcademico;
	
	private String contacto2;
	
	private String contacto3;
	
}
