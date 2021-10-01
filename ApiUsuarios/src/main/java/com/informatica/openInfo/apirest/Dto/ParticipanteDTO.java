package com.informatica.openInfo.apirest.Dto;


import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipanteDTO {
	
	private Usuario usuario;
		
	private String gradoAcademico;
	
	private String descripcion;
	


}
