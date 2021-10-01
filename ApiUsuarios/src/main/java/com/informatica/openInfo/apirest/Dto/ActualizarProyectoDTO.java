package com.informatica.openInfo.apirest.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarProyectoDTO {
	
	private Long idProyecto;
	
	private String linkOficial;
	
	private String descripcion;
	
	private Long idTipoProyecto;
	
	private boolean habilitado;
	
	private String area;
	
	private String areaProyecto; //"feria" o "concurso"
}
