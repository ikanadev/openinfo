package com.informatica.openInfo.apirest.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Entity
@Data
public class Usuario implements Serializable{
	
	@Id
	@Column(unique = true)
	@NotNull
	public String codRegistro;
	
	@Column(unique = true)
	@NotNull
	private String nombre;
	
	private String sexo;
	
	@Email
	@NotNull
	@Column(unique = true)
	private String correo;
	
	@NotNull
	private String password;
	
	private boolean habilitado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt=new Date();
	}
	
	private static final long serialVersionUID = 1L;

}
