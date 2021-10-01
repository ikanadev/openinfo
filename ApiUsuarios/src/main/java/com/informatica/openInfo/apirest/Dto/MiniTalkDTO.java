package com.informatica.openInfo.apirest.Dto;

import java.util.Date;

import com.informatica.openInfo.apirest.models.Usuario;

import lombok.Data;

@Data
public class MiniTalkDTO {
	
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	private String banner;
	
	private String linkOficial;
	
	private Date hora;
	
	private Date fecha;
	
	private Usuario expositor;
	
	private String gradoAcademico;
	
	private String institucion;
	
	private String foto;
	
	private String telefono;
	
	private Long vistas;
	
	private String codigo;

}
