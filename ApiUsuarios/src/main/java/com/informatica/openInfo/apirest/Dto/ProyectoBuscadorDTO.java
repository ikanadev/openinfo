package com.informatica.openInfo.apirest.Dto;

import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProyectoBuscadorDTO {
	
	private Long id;
	
	private String nombre;
	
	private Usuario encargado;

}
