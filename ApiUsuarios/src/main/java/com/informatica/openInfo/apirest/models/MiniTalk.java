package com.informatica.openInfo.apirest.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class MiniTalk implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	@NotNull
	private String nombre;
	
	@NotNull
	private String descripcion;
	
	@NotNull
	private String banner;
	
	@NotNull
	private String video;
	
	private String linkOficial;
	
	private Date hora;
	
	private Date fecha;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Gestion gestion;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario expositor;
	
	@NotNull
	private String gradoAcademico;
	
	private String institucion;
	
	@NotNull
	private String telefono;
	
	@NotNull
	private String foto;
	
	private Long vistas;
	
	private String codigo;
	
	private boolean habilitado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@PrePersist
	public void prePersist() {
		createdAt=new Date();
	}
	
	
	private static final long serialVersionUID = 1L;

}
