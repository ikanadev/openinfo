package com.informatica.openInfo.apirest.Dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegistrarEquipoDTO implements Serializable{
	
	private Long idTipoEquipo;
	
	private String nombre;
	
	private String codRegistro;
	
	private static final long serialVersionUID = 1L;

}
