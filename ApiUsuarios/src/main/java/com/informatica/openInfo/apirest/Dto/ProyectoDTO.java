package com.informatica.openInfo.apirest.Dto;

import java.util.List;

import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.TipoProyecto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProyectoDTO {
	
	private Long id;
	
	private String nombre;
	
	private String problematica;
	
	private String objetivoGeneral;
	
	private List<String> objetivosEspecificos;
	
	private String alcance;
	
	private String beneficiarios;
	
	private String valorAgregado;
	
	private String descripcion;
	
	private String banner;
	
	private String linkVideo;
	
	private String linkOficial;
	
	private Long vistas;
	
	private String codigo;
	
	private String area;
	
	private TipoProyecto tipoProyecto;
	
	private Equipo equipo;
	
	private List<ParticipanteDTO> participantes;
	
	

}
