package com.informatica.openInfo.apirest.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Gestion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String gestion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaIni;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	private String periodo;
	
	private boolean estado;
	
	private boolean habilitado;
	
	private static final long serialVersionUID = 1L;

}
