package com.informatica.openInfo.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informatica.openInfo.apirest.Dto.EquipoDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarEquipoDTO;
import com.informatica.openInfo.apirest.Dto.TipoEquipoDTO;
import com.informatica.openInfo.apirest.Dto.UsuarioDTO;
import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.TipoEquipo;
import com.informatica.openInfo.apirest.models.TipoProyecto;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;
import com.informatica.openInfo.apirest.services.EmailService;
import com.informatica.openInfo.apirest.services.IEquipoService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IRolService;
import com.informatica.openInfo.apirest.services.IUsuarioRolService;
import com.informatica.openInfo.apirest.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class EquipoRestController {
	
	private static final String URL="http://200.105.169.29:8080/login";
	
	@Autowired
	private IEquipoService equipoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private IUsuarioRolService usuarioRolService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Autowired
	private EmailService emailService;
	
	@Operation(summary = "- Registra un equipo(materia o grupo de estudio) y le asigna un encargado")
	@PostMapping("/registrarEquipo")
	public ResponseEntity<?> save(@RequestBody RegistrarEquipoDTO equipo) {

		Equipo nuevoEquipo = new Equipo();
		UsuarioRol usuarioRol=new UsuarioRol();
		Map<String, Object> response = new HashMap<>();
		Gestion gestion=new Gestion();
		try {
		
			Usuario encargado=usuarioService.findById(equipo.getCodRegistro());
			TipoEquipo tipoEquipo=equipoService.findByTipoEquipo(equipo.getIdTipoEquipo());
			Rol rol=rolService.findById(Long.valueOf(4));
			gestion=proyectoService.findByHabilitadoTrue();
			//asignar datos al equipo
			nuevoEquipo.setNombre(equipo.getNombre());
			nuevoEquipo.setEncargado(encargado);
			nuevoEquipo.setTipoEquipo(tipoEquipo);
			nuevoEquipo.setGestion(gestion);
			equipoService.save(nuevoEquipo);
			
			//asignar rol a usuario
			UsuarioRolKey key=new UsuarioRolKey();
			key.setCodRegistro(encargado.getCodRegistro());
			key.setIdRol(rol.getIdRol());
			usuarioRol.setId(key);
			usuarioRol.setUsuario(encargado);
			usuarioRol.setRol(rol);
			usuarioRolService.save(usuarioRol);
			String content="Se le registro como jefe del equipo "+nuevoEquipo.getNombre()+ "\n"+
					"para administrar sus proyectos puede acceder con su correo registrado y su codigo de registro a: "+"\n"
					+URL;
			emailService.mandaEmail(encargado.getCorreo(), "OPEN INFO SUPPORT", content);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("equipo", nuevoEquipo);
		response.put("asigna rol", usuarioRol);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Lista todos los tipos de Equipo")
	@GetMapping("/tipoEquipo")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<TipoEquipo> equipos = new ArrayList<TipoEquipo>();
		try {
			equipos = equipoService.listaTipoEquipo();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("tipoEquipos", equipos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Lista de equipos ordenados por tipo de equipo")
	@GetMapping("/equipos")
	public ResponseEntity<?> listaPorTipoEquipo() {
		Map<String, Object> response = new HashMap<>();
		List<TipoEquipo> tipoEquipos = new ArrayList<TipoEquipo>();
		List<TipoEquipoDTO> tipoEquipoDTOs=new ArrayList<TipoEquipoDTO>();
		ModelMapper mapper=new ModelMapper();
		Gestion gestion =new Gestion();
		try {
			tipoEquipos = equipoService.listaTipoEquipo();
			gestion = proyectoService.findByHabilitadoTrue();
			for(TipoEquipo eq: tipoEquipos) {
				TipoEquipoDTO tipoEquipoDTO=mapper.map(eq, TipoEquipoDTO.class);
				
				List<Equipo> equipos=equipoService.findByTipoEquipo_Id(eq.getId(),gestion.getId());
				List<EquipoDTO> equiposDTO=new ArrayList<EquipoDTO>();
				for(Equipo eqq : equipos) {
					EquipoDTO equipoDTO = mapper.map(eqq, EquipoDTO.class);
					equiposDTO.add(equipoDTO);
				}
				tipoEquipoDTO.setEquipos(equiposDTO);
				tipoEquipoDTOs.add(tipoEquipoDTO);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("tipoEquipos", tipoEquipoDTOs);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Devuelve todos los Equipos de un encargado")
	@GetMapping("/equiposUsuario/{codRegistro}")
	public ResponseEntity<?> equiposDeEncargado(@PathVariable String codRegistro) {
		Map<String, Object> response = new HashMap<>();
		List<Equipo> equipos = new ArrayList<Equipo>();
		Gestion gestion =new Gestion();
		try {
			gestion = proyectoService.findByHabilitadoTrue();
			equipos = equipoService.findByEncargado_codRegistro(codRegistro, gestion.getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("equipos", equipos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
