package com.informatica.openInfo.apirest.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarMiniTalkDTO {
	
	private String nombre;
	
	private String sexo;
	
	private String correo;
	
	private String gradoAcademico;
	
	private String foto;
	
	private String telefono;
	
	private String institucion;
	
	private String banner;
	
	private String titulo;
	
	private String descripcion;
	
	private String video;

}
