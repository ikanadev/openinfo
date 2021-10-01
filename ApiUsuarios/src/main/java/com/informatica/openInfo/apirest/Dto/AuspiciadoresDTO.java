package com.informatica.openInfo.apirest.Dto;

import java.io.Serializable;

import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuspiciadoresDTO implements Serializable{


	private Long id;
	
	private String nombre;
	
	private String logo;
	
	private String descripcion;
	
	private String link;
	
	private String contacto;
	
	

	private static final long serialVersionUID = 1L;

}
