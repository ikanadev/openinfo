package com.informatica.openInfo.apirest.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.engine.jdbc.spi.TypeSearchability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informatica.openInfo.apirest.Dto.ActualizarGestionDTO;
import com.informatica.openInfo.apirest.Dto.GestionDTO;
import com.informatica.openInfo.apirest.Dto.HabiltarGestionDTO;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.services.IProyectoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class GestionRestController {
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Operation(summary = "- Lista todas las versiones con estado=true de la open info")
	@GetMapping("/gestionesHabilitadas")
	public ResponseEntity<?> gestionesHabilitadas() {
		Map<String, Object> response = new HashMap<>();
		List<Gestion> gestiones = new ArrayList<Gestion>();
		try {
			gestiones = proyectoService.listarGestiones();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("gestiones", gestiones);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Lista todas las versiones de la open info")
	@GetMapping("/listaGestiones")
	public ResponseEntity<?> ListaGestiones() {
		Map<String, Object> response = new HashMap<>();
		List<Gestion> gestiones = new ArrayList<Gestion>();
		try {
			gestiones = proyectoService.allGestiones();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("gestiones", gestiones);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Registra una nueva gestion")
	@PostMapping("/registrarGestion")
	public ResponseEntity<?> registrarGestion(@RequestBody GestionDTO gestionDTO) {
		Map<String, Object> response = new HashMap<>();
		Gestion gestion = new Gestion();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		Date fechaIni = new Date();
		
		Date fechaFin=new Date();
		try {
			try {
				fechaIni=format.parse(gestionDTO.getFechaIni());
				fechaFin=format.parse(gestionDTO.getFechaFin());
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			gestion.setFechaFin(fechaFin);
			gestion.setFechaIni(fechaIni);
			gestion.setHabilitado(false);
			gestion.setPeriodo(gestionDTO.getPeriodo());
			gestion.setGestion(gestionDTO.getFechaFin().substring(0, 4));
			proyectoService.registrarGestion(gestion);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al registrar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("gestion", gestion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Pone en estado activo una gestion (todos los registros se almacenan con la gestion activa)")
	@PostMapping("/activarGestion")
	public ResponseEntity<?> activarGestion(@RequestBody ActualizarGestionDTO actualizarGestionDTO) {
		Map<String, Object> response = new HashMap<>();
		Gestion gestion = new Gestion();
		List<Gestion> gestiones = new ArrayList<Gestion>();
		try {
			gestion=proyectoService.buscarGestionPorId(actualizarGestionDTO.getIdGestion());
			if(gestion ==null) {
				response.put("mensaje", "no existe la region con el id enviado");
			}else {
				gestiones = proyectoService.listarGestiones();
				for(Gestion g:gestiones) {
					if(actualizarGestionDTO.getIdGestion()==g.getId()) {
						g.setHabilitado(true);
					}else {
						g.setHabilitado(false);
					}
					proyectoService.registrarGestion(g);
				}
				response.put("mensaje", "gestion activa "+actualizarGestionDTO.getIdGestion());
			}
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Habilitar una gestion para que sea visible en la lista de frontend del cliente")
	@PostMapping("/habilitarGestion")
	public ResponseEntity<?> habilitarGestion(@RequestBody HabiltarGestionDTO habilitarGestionDTO) {
		Map<String, Object> response = new HashMap<>();
		Gestion gestion = new Gestion();
		
		try {
			gestion=proyectoService.buscarGestionPorId(habilitarGestionDTO.getIdGestion());
			if(gestion ==null) {
				response.put("mensaje", "no existe la region con el id enviado");
			}else {
				gestion.setEstado(habilitarGestionDTO.isEstado());
				proyectoService.registrarGestion(gestion);
				response.put("gestion", gestion);
			}
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
