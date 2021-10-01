package com.informatica.openInfo.apirest.models.embedKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UsuarioActividadKey implements Serializable{
	
	private String codRegistro;
	
	private String idActividad;
	
	private static final long serialVersionUID = 1L;

}
