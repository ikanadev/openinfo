package com.informatica.openInfo.apirest.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Entity
@Data
public class Equipo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEquipo;
	
	private String nombre;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TipoEquipo tipoEquipo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario encargado;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Gestion gestion;
	
	private boolean habilitado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt=new Date();
	}

	private static final long serialVersionUID = 1L;
}
