package com.informatica.openInfo.apirest.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.informatica.openInfo.apirest.Dto.EquipoDTO;
import com.informatica.openInfo.apirest.Dto.ParticipanteDTO;
import com.informatica.openInfo.apirest.Dto.ParticipanteProyectoDTO;
import com.informatica.openInfo.apirest.Dto.ParticipantesDetalleDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarEquipoDTO;
import com.informatica.openInfo.apirest.Dto.TipoEquipoDTO;
import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.Participante;
import com.informatica.openInfo.apirest.models.ParticipanteProyecto;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.TipoEquipo;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.services.IEquipoService;
import com.informatica.openInfo.apirest.services.IParticipanteService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IUploadFileService;
import com.informatica.openInfo.apirest.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class ParticipantesRestController {

	@Autowired
	private IEquipoService equipoService;

	@Autowired
	private IParticipanteService participanteService; 
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Autowired
	private IUploadFileService fileService;


	@Operation(summary = "- Lista de todos los participantes por equipos")
	@GetMapping("/participantes")
	public ResponseEntity<?> listaParticipantes() {
		Map<String, Object> response = new HashMap<>();
		List<TipoEquipo> tipoEquipos = new ArrayList<TipoEquipo>();
		List<TipoEquipoDTO> tipoEquipoDTOs=new ArrayList<TipoEquipoDTO>();
		ModelMapper mapper=new ModelMapper();
		Gestion gestion=new Gestion();
		try {
			tipoEquipos = equipoService.listaTipoEquipo();
			gestion = proyectoService.findByHabilitadoTrue();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		for(TipoEquipo eq: tipoEquipos) {
			TipoEquipoDTO tipoEquipoDTO=mapper.map(eq, TipoEquipoDTO.class);
			List<Equipo> equipos=equipoService.findByTipoEquipo_Id(eq.getId(),gestion.getId());
			List<EquipoDTO> equiposDTO=new ArrayList<EquipoDTO>();
			for(Equipo eqq : equipos) {
				EquipoDTO equipoDTO = mapper.map(eqq, EquipoDTO.class);
				List<Participante> participantes = participanteService.listarPorEquipo(eqq.getIdEquipo());
				List<ParticipanteDTO> participantesDTO =new ArrayList<>();
				for(Participante pa : participantes) {
					ParticipanteDTO participanteDTO = mapper.map(pa, ParticipanteDTO.class);
					participantesDTO.add(participanteDTO);
				}
				equipoDTO.setParticipantes(participantesDTO);
				equiposDTO.add(equipoDTO);
			}
			tipoEquipoDTO.setEquipos(equiposDTO);
			tipoEquipoDTOs.add(tipoEquipoDTO);
		}
		response.put("Participantes por equipos", tipoEquipoDTOs);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Lista los participantes de proyectos concurso")
	@GetMapping("/participantesConcurso")
	public ResponseEntity<?> listarParticipantesConcursos() {
		Map<String, Object> response = new HashMap<>();
		List<Equipo> equiposActividad = new ArrayList<Equipo>();
		List<EquipoDTO> equipos = new ArrayList<EquipoDTO>();
		Gestion gestion=new Gestion();
		ModelMapper modelMapper = new ModelMapper();
		try {
			gestion = proyectoService.findByHabilitadoTrue();
			equiposActividad = equipoService.listaEquiposConcursos(gestion.getId());
			
			for (Equipo eq : equiposActividad) {
				EquipoDTO equipoDTO = modelMapper.map(eq, EquipoDTO.class);
//				List<ParticipanteDTO> participantes = new ArrayList<ParticipanteDTO>();
//				List<ParticipanteEquipo> participantesEquipo = participanteEquipoService
//						.listaParticipantesEquipo(eq.getIdEquipo());
//				for (ParticipanteEquipo part : participantesEquipo) {
//					Participante p = part.getParticipante();
//					ParticipanteDTO participanteDTO = modelMapper.map(p, ParticipanteDTO.class);
//					participantes.add(participanteDTO);
//				}
//				equipoDTO.setParticipantes(participantes);
				equipos.add(equipoDTO);
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (equiposActividad.isEmpty()) {
			response.put("mensaje", "No existen participantes registrados");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("participantes", equipos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	@Operation(summary = "- Devuelve datos de un participante dado su nombre")
	@GetMapping("/participantes/{nombre}")
	public ResponseEntity<?> findById(@PathVariable String nombre) {

		Participante participante = new Participante();
		ParticipantesDetalleDTO participanteDTO = new ParticipantesDetalleDTO();
		Map<String, Object> response = new HashMap<>();
		ModelMapper maper=new ModelMapper();
		try {
			participante = participanteService.findByNombre(nombre);
			

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (participante == null) {
			response.put("mensaje", "El participante de nombre: ".concat(nombre).concat(" no está registrado en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}else {
			participanteDTO = maper.map(participante, ParticipantesDetalleDTO.class);
		}
		response.put("participante", participanteDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- registrar participante en un proyecto")
	@PostMapping("/agregarParticipante")
	public ResponseEntity<?> save(@RequestParam("ci") String ci, @RequestParam("foto") MultipartFile foto,
			@RequestParam("gradoAcademico") String gradoAcademico, @RequestParam("telefono") String telefono,
			@RequestParam("contacto2") String contacto2, @RequestParam("contacto3") String contacto3,
			@RequestParam("descripcion") String descripcion, @RequestParam("codRegistro") String codRegistro,
			@RequestParam("idProyecto") Long idProyecto) {
		ParticipanteProyecto nuevoParticipanteProyecto = new ParticipanteProyecto();
		Participante nuevoParticipante = new Participante();
		Proyecto proyecto = new Proyecto();
		Map<String, Object> response = new HashMap<>();
		String nombreFoto = "";
		try {
			Usuario user=usuarioService.findById(codRegistro);
			proyecto=proyectoService.findById(idProyecto);
			try {
				nombreFoto = fileService.copiar(foto);
			} catch (IOException e) {				
				response.put("mensaje", "Error al subir la imagen del proyecto");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			nuevoParticipante=participanteService.findByUsuario_CodRegistro(codRegistro);
			if(nuevoParticipante ==null) {
				nuevoParticipante = new Participante();
				nuevoParticipante.setCi(ci);
				nuevoParticipante.setContacto2(contacto2);
				nuevoParticipante.setContacto3(contacto3);
				nuevoParticipante.setDescripcion(descripcion);
				nuevoParticipante.setFoto(nombreFoto);
				nuevoParticipante.setGradoAcademico(gradoAcademico);
				nuevoParticipante.setTelefono(telefono);
				nuevoParticipante.setUsuario(user);
				participanteService.save(nuevoParticipante);
			}
			nuevoParticipanteProyecto.setParticipante(nuevoParticipante);
			nuevoParticipanteProyecto.setProyecto(proyecto);
			nuevoParticipanteProyecto.setRepresentante(false);
			proyectoService.nuevoParticipanteProyecto(nuevoParticipanteProyecto);
		

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("participante", nuevoParticipante);
		response.put("proyecto", proyecto);
		response.put("participanteProyecto", nuevoParticipanteProyecto);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
