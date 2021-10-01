package com.informatica.openInfo.apirest.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.informatica.openInfo.apirest.Dto.JuradoDTO;
import com.informatica.openInfo.apirest.Dto.JuradoProyectosDTO;
import com.informatica.openInfo.apirest.Dto.MiniTalkRankingDTO;
import com.informatica.openInfo.apirest.Dto.ParticipanteDTO;
import com.informatica.openInfo.apirest.Dto.ParticipantesDetalleDTO;
import com.informatica.openInfo.apirest.Dto.ProyectoDTO;
import com.informatica.openInfo.apirest.Dto.ProyectosConNotaDTO;
import com.informatica.openInfo.apirest.Dto.ProyectosMasVistosDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarJuradoDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarNotaDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarVistaDTO;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.Jurado;
import com.informatica.openInfo.apirest.models.JuradoProyectos;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.Participante;
import com.informatica.openInfo.apirest.models.ParticipanteProyecto;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;
import com.informatica.openInfo.apirest.services.IJuradoService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IRolService;
import com.informatica.openInfo.apirest.services.IUsuarioRolService;
import com.informatica.openInfo.apirest.services.IUsuarioService;
import com.informatica.openInfo.apirest.services.UsuarioServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class JuradosRestController {

	@Autowired
	private IJuradoService juradoService;

	@Autowired
	private IProyectoService proyectoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IUsuarioRolService usuarioRolService;

	@Operation(summary = "- Devuelve el ranking (top 10 proyectos y talks mas vistos) y top 10 mejor evaluados concurso")
	@GetMapping("/ranking")
	public ResponseEntity<?> rankingProyectos(@RequestParam("gestionId") Long gestionId) {
		Map<String, Object> response = new HashMap<>();
		List<Proyecto> proyectosConcurso = new ArrayList<Proyecto>();
		List<Proyecto> proyectosFeria = new ArrayList<Proyecto>();
		List<MiniTalk> miniTalks = new ArrayList<MiniTalk>();
		List<ProyectosConNotaDTO> proyectosNota = new ArrayList<ProyectosConNotaDTO>();
		List<ProyectosMasVistosDTO> proyectosMasVistos = new ArrayList<ProyectosMasVistosDTO>();
		List<MiniTalkRankingDTO> talksRanking = new ArrayList<MiniTalkRankingDTO>();
		try {
			Gestion gestion=proyectoService.buscarGestionPorId(gestionId);
			proyectosConcurso = proyectoService.topProyectos(gestion.getId(), "concurso");
			proyectosFeria = proyectoService.topProyectos(gestion.getId(), "feria");
			miniTalks = proyectoService.topTalks(gestion.getId());
			for (Proyecto pry : proyectosConcurso) {
				List<JuradoProyectos> juradosProyecto = juradoService.findByProyecto_Id(pry.getId());
				ProyectosConNotaDTO proyectoConNota = new ProyectosConNotaDTO();
				DecimalFormat df = new DecimalFormat("#.00");
				Double total = 0.0;
				for (JuradoProyectos jp : juradosProyecto) {
					Double notaJurado = jp.getFuncionalidad() + jp.getImpacto() + jp.getInnovacion()
							+ jp.getPresentacion() + jp.getUx();

					total = total + notaJurado;

				}
				total = total / juradosProyecto.size();
				String tot = df.format(total);
				proyectoConNota.setIdProyecto(pry.getId());
				proyectoConNota.setNombre(pry.getNombre());
				proyectoConNota.setCalificacion(tot);
				proyectoConNota.setLinkOficial(pry.getLinkOficial());
				proyectoConNota.setVistas(pry.getVistas());
				proyectosNota.add(proyectoConNota);

			}
			for (Proyecto pry : proyectosFeria) {

				ProyectosMasVistosDTO proyectoConNota = new ProyectosMasVistosDTO();
				proyectoConNota.setIdProyecto(pry.getId());
				proyectoConNota.setNombre(pry.getNombre());
				proyectoConNota.setVistas(pry.getVistas());
				proyectoConNota.setLinkOficial(pry.getLinkOficial());
				proyectosMasVistos.add(proyectoConNota);
			}
			for (MiniTalk talk : miniTalks) {

				MiniTalkRankingDTO talkDTO = new MiniTalkRankingDTO();
				talkDTO.setId(talk.getId());
				talkDTO.setNombre(talk.getNombre());
				talkDTO.setVistas(talk.getVistas());
				talkDTO.setLinkOficial(talk.getLinkOficial());
				talksRanking.add(talkDTO);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// ordenar lista ranking descendente
		Collections.sort(proyectosNota, new Comparator<ProyectosConNotaDTO>() {

			@Override
			public int compare(ProyectosConNotaDTO o1, ProyectosConNotaDTO o2) {
				return o1.getCalificacion().compareTo(o2.getCalificacion());
			}
		});
		Collections.reverse(proyectosNota);
		response.put("mejor_puntuados", proyectosNota);
		response.put("mas_vistos_feria", proyectosMasVistos);
		response.put("talks_mas_vistos", talksRanking);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Asigna un nuevo jurado a un proyecto")
	@PostMapping("/registrarJurado")
	public ResponseEntity<?> registrarJurado(@RequestBody RegistrarJuradoDTO registrarJuradoDTO) {

		Map<String, Object> response = new HashMap<>();
		Usuario usuario = new Usuario();
		Jurado jurado = new Jurado();
		Proyecto proyecto = new Proyecto();
		Rol rol = rolService.findById(Long.valueOf(3));
		JuradoProyectos juradoProyecto = new JuradoProyectos();
		UsuarioRol usuarioRol = new UsuarioRol();
		
		try {
			usuario = usuarioService.findById(registrarJuradoDTO.getCodRegistro());
			proyecto = proyectoService.findById(registrarJuradoDTO.getIdProyecto());
			jurado = juradoService.findByCodRegistro(registrarJuradoDTO.getCodRegistro());
			if (jurado == null) {
				jurado = new Jurado();
				jurado.setUsuario(usuario);
				jurado.setTelefono(registrarJuradoDTO.getTelefono());
				jurado.setGradoAcademico(registrarJuradoDTO.getGradoAcademico());
				juradoService.save(jurado);
			}
			jurado.setGradoAcademico(registrarJuradoDTO.getGradoAcademico());
			jurado.setTelefono(registrarJuradoDTO.getTelefono());
			juradoService.save(jurado);
			juradoProyecto = juradoService.findByProyecto_IdAndJurado_Usuario_CodRegistro(
					registrarJuradoDTO.getIdProyecto(), registrarJuradoDTO.getCodRegistro());
			if (juradoProyecto == null) {
				juradoProyecto = new JuradoProyectos();
				juradoProyecto.setProyecto(proyecto);
				juradoProyecto.setJurado(jurado);
				juradoService.registrarJurado(juradoProyecto);
				response.put("registroJurado", juradoProyecto);
				response.put("mensaje", "El jurado ha sido asignado con éxito!");
				UsuarioRolKey key = new UsuarioRolKey();
				key.setCodRegistro(usuario.getCodRegistro());
				key.setIdRol(rol.getIdRol());
				usuarioRol.setId(key);
				usuarioRol.setUsuario(usuario);
				usuarioRol.setRol(rol);
				usuarioRolService.save(usuarioRol);
			} else {
				response.put("mensaje", "el jurado ya se encuantra asignado a ese proyecto");
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "- Devuelve la lista de jurados")
	@GetMapping("/jurados")
	public ResponseEntity<?> listaDeJurados() {

		Map<String, Object> response = new HashMap<>();
		List<JuradoDTO> jurados = new ArrayList<JuradoDTO>();
		List<Jurado> listaJurados = new ArrayList<Jurado>();
		Gestion gestion=new Gestion();
		boolean sw=true;
		ModelMapper maper = new ModelMapper();
		try {
			listaJurados = juradoService.findAll();
			gestion=proyectoService.findByHabilitadoTrue();
			if (listaJurados == null) {
				listaJurados = new ArrayList<Jurado>();

			} else {
				for (Jurado j : listaJurados) {
					sw=true;
					JuradoDTO juradoDTO = maper.map(j, JuradoDTO.class);
					List<JuradoProyectos> juradoProyectos = juradoService.findByJurado_Id(j.getId());
					List<JuradoProyectosDTO> juradoProyectosDTO = new ArrayList<JuradoProyectosDTO>();
					for (JuradoProyectos jp : juradoProyectos) {
						if(jp.getProyecto().getGestion().getId()==gestion.getId()) {
							JuradoProyectosDTO juradoProyectoDTO = maper.map(jp, JuradoProyectosDTO.class);
							juradoProyectosDTO.add(juradoProyectoDTO);
						}else {
							sw=false;
						}
						
					}
					if(sw) {
						juradoDTO.setProyectos(juradoProyectosDTO);
						jurados.add(juradoDTO);
					}
					
				}

			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("jurados", jurados);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Califica un proyecto")
	@PostMapping("/registrarNota")
	public ResponseEntity<?> registrarNota(@RequestBody RegistrarNotaDTO registrarNotaDTO) {

		Map<String, Object> response = new HashMap<>();
		JuradoProyectos juradoProyecto = new JuradoProyectos();

		try {
			juradoProyecto = juradoService.findByProyecto_IdAndJurado_Usuario_CodRegistro(
					registrarNotaDTO.getIdProyecto(), registrarNotaDTO.getCodRegistro());
			if (juradoProyecto == null) {
				response.put("mensaje", "el jurado no se encuentra asignado al proyecto");
			} else {
				juradoProyecto.setFuncionalidad(registrarNotaDTO.getFuncionalidad());
				juradoProyecto.setImpacto(registrarNotaDTO.getImpacto());
				juradoProyecto.setInnovacion(registrarNotaDTO.getInnovacion());
				juradoProyecto.setPresentacion(registrarNotaDTO.getPresentacion());
				juradoProyecto.setUx(registrarNotaDTO.getUx());
				juradoService.registrarJurado(juradoProyecto);
				response.put("registrado", juradoProyecto);
				response.put("mensaje", "Calificó el proyecto con exito!");
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
