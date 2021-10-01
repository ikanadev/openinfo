package com.informatica.openInfo.apirest.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;


@Entity
@Data
public class Proyecto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	//datos adicionales sujetos a evaluacion
	private String problematica;
	
	private String objetivoGeneral;
	
	private String objetivosEspecificos;
	
	private String alcance;
	
	private String beneficiarios;
	
	private String valorAgregado;
	
	//documento adicional (triptico, folleto, etc.)
	private String documento;
	//descripcion del proyecto
	private String descripcion;
	//banner promocional si tuviese o fondo cards front
	private String banner;
	
	//link video enviado por participantes
	private String linkVideo;
	
	//link oficial canal de la open info
	private String linkOficial;
	
	
	private String area; //feria o concurso
	
	//cantidad de visualizaciones del video 
	private Long vistas;
	
	//codigo extra de identificacion proyecto
	private String codigo;
	 
	//si está aprobado o no para participar en el evento
	private boolean habilitado;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TipoProyecto tipoProyecto;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Equipo equipo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Gestion gestion;
	
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt=new Date();
	}
	
	private static final long serialVersionUID = 1L;
	
	

}
