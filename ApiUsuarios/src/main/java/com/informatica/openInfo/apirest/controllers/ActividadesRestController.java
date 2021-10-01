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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.informatica.openInfo.apirest.Dto.ActualizarProyectoDTO;
import com.informatica.openInfo.apirest.Dto.MiniTalkDTO;
import com.informatica.openInfo.apirest.Dto.ParticipanteDTO;
import com.informatica.openInfo.apirest.Dto.ProyectoBuscadorDTO;
import com.informatica.openInfo.apirest.Dto.ProyectoDTO;
import com.informatica.openInfo.apirest.Dto.ProyectoPorTipoDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarProyectoDTO;
import com.informatica.openInfo.apirest.Dto.RegistrarVistaDTO;
import com.informatica.openInfo.apirest.config.CodeGenerator;
import com.informatica.openInfo.apirest.models.Equipo;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.Participante;
import com.informatica.openInfo.apirest.models.ParticipanteProyecto;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.TipoProyecto;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;
import com.informatica.openInfo.apirest.services.EmailService;
import com.informatica.openInfo.apirest.services.IEquipoService;
import com.informatica.openInfo.apirest.services.IParticipanteService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IRolService;
import com.informatica.openInfo.apirest.services.IUploadFileService;
import com.informatica.openInfo.apirest.services.IUsuarioRolService;
import com.informatica.openInfo.apirest.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class ActividadesRestController {

	private static final String URL = "http://200.105.169.29:8080/login";

	@Autowired
	private IProyectoService proyectoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IEquipoService equipoService;

	@Autowired
	private IParticipanteService participanteService;

	@Autowired
	private IUsuarioRolService usuarioRolService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IUploadFileService fileService;

	@Autowired
	private CodeGenerator codegenerator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Operation(summary = "- Lista de minitalks habilitados de la gestion seleccionada")
	@GetMapping("/actividades/miniTalk")
	public ResponseEntity<?> listaMiniTalk(@RequestParam("gestionId") Long gestionId) {
		Map<String, Object> response = new HashMap<>();
		List<MiniTalk> talks = new ArrayList<MiniTalk>();

		try {
			Gestion gestion = proyectoService.buscarGestionPorId(gestionId);
			talks = proyectoService.miniTalks(gestion.getId());

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<MiniTalkDTO> listaActividades = new ArrayList<MiniTalkDTO>();
		for (MiniTalk mini : talks) {
			ModelMapper mapper = new ModelMapper();
			MiniTalkDTO miniTalkDTO = mapper.map(mini, MiniTalkDTO.class);

			listaActividades.add(miniTalkDTO);
		}
		response.put("miniTalks", listaActividades);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Lista de proyectos habilitados que participan en la feria de la gestion seleccionada")
	@GetMapping("/actividades/proyectosFeria")
	public ResponseEntity<?> listaProyectosFeria(@RequestParam("gestionId") Long gestionId) {
		Map<String, Object> response = new HashMap<>();
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		try {
			Gestion gestion = proyectoService.buscarGestionPorId(gestionId);
			proyectos = proyectoService.findByAreaLike(gestion.getId(), "feria");
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<ProyectoDTO> listaProyectos = new ArrayList<ProyectoDTO>();
		for (Proyecto act : proyectos) {
			ModelMapper mapper = new ModelMapper();
			ProyectoDTO proyectoDTO = mapper.map(act, ProyectoDTO.class);
			participantes = proyectoService.listaParticipantes(act.getId());
			List<Participante> particip = new ArrayList<Participante>();
			List<String> objetivosStrings = new ArrayList<String>();
			if (act.getObjetivosEspecificos() != null) {
				String[] objetivos = act.getObjetivosEspecificos().split("_");
				for (String obj : objetivos) {

					objetivosStrings.add(obj);

				}
			}

			for (ParticipanteProyecto parta : participantes) {
				Participante par = parta.getParticipante();
				particip.add(par);
			}
			List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
			for (Participante pa : particip) {
				ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
				participanteDTO.add(participante);
			}

			proyectoDTO.setParticipantes(participanteDTO);
			proyectoDTO.setObjetivosEspecificos(objetivosStrings);
			listaProyectos.add(proyectoDTO);
		}
		response.put("proyectosFeria", listaProyectos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Lista de proyectos habilitados que participan en el concurso de la gestion seleccionada")
	@GetMapping("/actividades/proyectosConcurso")
	public ResponseEntity<?> listaConcursos(@RequestParam("gestionId") Long gestionId) {
		Map<String, Object> response = new HashMap<>();
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		try {
			Gestion gestion = proyectoService.buscarGestionPorId(gestionId);
			proyectos = proyectoService.findByAreaLike(gestion.getId(), "concurso");
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<ProyectoDTO> listaProyectos = new ArrayList<ProyectoDTO>();
		for (Proyecto act : proyectos) {
			ModelMapper mapper = new ModelMapper();
			ProyectoDTO proyectoDTO = mapper.map(act, ProyectoDTO.class);
			participantes = proyectoService.listaParticipantes(act.getId());
			List<Participante> particip = new ArrayList<Participante>();
			List<String> objetivosStrings = new ArrayList<String>();
			if (act.getObjetivosEspecificos() != null) {
				String[] objetivos = act.getObjetivosEspecificos().split("_");
				for (String obj : objetivos) {

					objetivosStrings.add(obj);

				}
			}

			for (ParticipanteProyecto parta : participantes) {
				Participante par = parta.getParticipante();
				particip.add(par);
			}
			List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
			for (Participante pa : particip) {
				ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
				participanteDTO.add(participante);
			}

			proyectoDTO.setParticipantes(participanteDTO);
			proyectoDTO.setObjetivosEspecificos(objetivosStrings);
			listaProyectos.add(proyectoDTO);
		}
		response.put("proyectosConcurso", listaProyectos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Lista de los tipos de Proyectos")
	@GetMapping("/tipoProyectos")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<TipoProyecto> proyectos = null;
		try {
			proyectos = proyectoService.listaTipoProyectos();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("tipoProyectos", proyectos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Registra un nuevo proyecto")
	@PostMapping("/registrarProyecto")
	public ResponseEntity<?> registrarProyecto(@RequestBody RegistrarProyectoDTO proyecto) {
		Map<String, Object> response = new HashMap<>();
		Proyecto nuevoProyecto = new Proyecto();
		Participante nuevoParticipante = new Participante();
		ParticipanteProyecto participanteProyecto = new ParticipanteProyecto();
		UsuarioRol usuarioRol = new UsuarioRol();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		Proyecto proyectoCreado = new Proyecto();

		// para desplegar en la respuesta

		try {
			// registrar proyecto
			Usuario jefeProyecto = usuarioService.findById(proyecto.getCodJefeProyecto());
			Equipo equipo = equipoService.findById(proyecto.getIdEquipo());
			Rol rol = rolService.findById(Long.valueOf(5));
			Gestion gestion = proyectoService.findByHabilitadoTrue();
			nuevoProyecto.setNombre(proyecto.getNombre());
			nuevoProyecto.setArea(proyecto.getArea());
			nuevoProyecto.setEquipo(equipo);
			nuevoProyecto.setGestion(gestion);
			nuevoProyecto.setHabilitado(true);
			nuevoProyecto.setVistas(Long.valueOf(1));
			nuevoProyecto.setCodigo(codegenerator.generarCodigoProyectos());
			nuevoProyecto.setAlcance("");
			nuevoProyecto.setBanner("");
			nuevoProyecto.setBeneficiarios("");
			nuevoProyecto.setDescripcion("");
			nuevoProyecto.setLinkOficial("");
			nuevoProyecto.setLinkVideo("");
			nuevoProyecto.setObjetivoGeneral("");
			nuevoProyecto.setObjetivosEspecificos("");
			nuevoProyecto.setProblematica("");
			nuevoProyecto.setValorAgregado("");
			// registrar jefe de proyecto
			nuevoParticipante = participanteService.findByUsuario_CodRegistro(jefeProyecto.getCodRegistro());
			if (nuevoParticipante == null) {
				nuevoParticipante = new Participante();
				nuevoParticipante.setUsuario(jefeProyecto);
				participanteService.save(nuevoParticipante);
			}
			participanteProyecto.setParticipante(nuevoParticipante);
			participanteProyecto.setProyecto(nuevoProyecto);
			participanteProyecto.setRepresentante(true);
			// asignar rol a jefe de proyecto
			UsuarioRolKey key = new UsuarioRolKey();
			key.setCodRegistro(jefeProyecto.getCodRegistro());
			key.setIdRol(rol.getIdRol());
			usuarioRol.setId(key);
			usuarioRol.setUsuario(jefeProyecto);
			usuarioRol.setRol(rol);
			// confirmar

			proyectoCreado = proyectoService.save(nuevoProyecto);
			proyectoService.nuevoParticipanteProyecto(participanteProyecto);
			usuarioRolService.save(usuarioRol);
			String content = "Se le registro como encargado del proyecto " + proyectoCreado.getNombre() + "\n"
					+ "para administrar su proyecto puede acceder con su correo registrado y su codigo de registro a: "
					+ "\n" + URL;
			emailService.mandaEmail(jefeProyecto.getCorreo(), "OPEN INFO SUPPORT",content);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ProyectoDTO proyectoDTO = new ProyectoDTO();
		ModelMapper mapper = new ModelMapper();
		proyectoDTO = mapper.map(proyectoCreado, ProyectoDTO.class);
		participantes = proyectoService.listaParticipantes(nuevoProyecto.getId());
		List<Participante> particip = new ArrayList<Participante>();
		List<String> objetivosStrings = new ArrayList<String>();
		if (proyectoCreado.getObjetivosEspecificos() != null) {
			String[] objetivos = proyectoCreado.getObjetivosEspecificos().split("_");
			for (String obj : objetivos) {

				objetivosStrings.add(obj);

			}
		}

		for (ParticipanteProyecto parta : participantes) {
			Participante par = parta.getParticipante();
			particip.add(par);
		}
		List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
		for (Participante pa : particip) {
			ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
			participanteDTO.add(participante);
		}
		proyectoDTO.setParticipantes(participanteDTO);
		proyectoDTO.setObjetivosEspecificos(objetivosStrings);
		response.put("proyecto", proyectoDTO);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Actualiza datos de un proyecto")
	@PostMapping("/actualizarProyecto/{idProyecto}")
	public ResponseEntity<?> actualizarProyecto(@PathVariable Long idProyecto, @RequestParam("nombre") String nombre,
			@RequestParam("problematica") String problematica, @RequestParam("objetivoGeneral") String objetivoGeneral,
			@RequestParam("alcance") String alcance, @RequestParam("beneficiarios") String beneficiarios,
			@RequestParam("valorAgregado") String valorAgregado, @RequestParam("descripcion") String descripcion,
			@RequestParam("linkVideo") String linkVideo, @RequestParam("idTipoProyecto") Long idTipoProyecto,
			@RequestParam("banner") MultipartFile banner, @RequestParam("objetivos") String objetivos,
			@RequestParam("linkVideoOficial") String linkVideoOficial,@RequestParam("documento")MultipartFile documento) {

		
		Proyecto proyectoUpdated = new Proyecto();
		Map<String, Object> response = new HashMap<>();

		String nombreArchivo = "";
		String document="";
		try {
			Proyecto proyectoActual = proyectoService.findById(idProyecto);
			String bannerAnterior=proyectoActual.getBanner();
			String documAnterior=proyectoActual.getDocumento();
			try {
				if(!(banner.getSize()==0)) {
					fileService.eliminar(bannerAnterior);
					nombreArchivo = fileService.copiar(banner);
					proyectoActual.setBanner(nombreArchivo);
				}
					
				if(!(documento.getSize()==0)) {
					fileService.eliminar(documAnterior);
					document = fileService.copiar(documento);
					proyectoActual.setDocumento(document);
				}
				
			} catch (IOException e) {
				response.put("mensaje", "Error al subir archivos del proyecto");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			proyectoActual.setNombre(nombre);
			proyectoActual.setProblematica(problematica);
			proyectoActual.setObjetivoGeneral(objetivoGeneral);
			proyectoActual.setAlcance(alcance);
			proyectoActual.setBeneficiarios(beneficiarios);
			proyectoActual.setValorAgregado(valorAgregado);
			proyectoActual.setDescripcion(descripcion);
	
			proyectoActual.setLinkOficial(linkVideoOficial);
			proyectoActual.setLinkVideo(linkVideo);
			proyectoActual.setObjetivosEspecificos(objetivos);
			proyectoActual.setTipoProyecto(proyectoService.findByTipoProyectoID(idTipoProyecto));
			proyectoUpdated = proyectoService.save(proyectoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el proyecto en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El proyecto ha sido actualizado con éxito!");
		response.put("proyecto", proyectoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "- Registra un nuevo Mini Talk")
	@PostMapping("/miniTalk")
	public ResponseEntity<?> registrarMiniTalk(@RequestParam("nombre") String nombre, @RequestParam("sexo") String sexo,
			@RequestParam("correo") String correo, @RequestParam("gradoAcademico") String gradoAcademico,
			@RequestParam("foto") MultipartFile foto, @RequestParam("telefono") String telefono,
			@RequestParam("institucion") String institucion, @RequestParam("banner") MultipartFile banner,
			@RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion,
			@RequestParam("linkVideo") String linkVideo) {
		Map<String, Object> response = new HashMap<>();
		Usuario nuevoUsuario = new Usuario();
		Usuario finalUser = new Usuario();
		MiniTalk nuevoTalk = new MiniTalk();
		Gestion gestion = new Gestion();
		String nombreBanner = "";
		String nombreFoto = "";
		try {
			// asignar datos al usuario
			gestion=proyectoService.findByHabilitadoTrue();
			nuevoUsuario.setCodRegistro(codegenerator.generarCodigoNuevo());
			nuevoUsuario.setPassword(passwordEncoder.encode(correo));
			nuevoUsuario.setNombre(nombre);
			nuevoUsuario.setCorreo(correo);
			nuevoUsuario.setSexo(sexo);
			nuevoUsuario.setHabilitado(true);
			finalUser = usuarioService.save(nuevoUsuario);
			// asignar datos al talk
			try {
				nombreBanner = fileService.copiar(banner);
				nombreFoto = fileService.copiar(foto);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir las imagenes del mini talk");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			nuevoTalk.setGestion(gestion);
			nuevoTalk.setGradoAcademico(gradoAcademico);
			nuevoTalk.setBanner(nombreBanner);
			nuevoTalk.setFoto(nombreFoto);
			nuevoTalk.setTelefono(telefono);
			nuevoTalk.setGestion(gestion);
			nuevoTalk.setInstitucion(institucion);
			nuevoTalk.setNombre(titulo);
			nuevoTalk.setDescripcion(descripcion);
			nuevoTalk.setVideo(linkVideo);
			nuevoTalk.setCodigo(codegenerator.generarCodigoTalks());
			nuevoTalk.setExpositor(finalUser);

			proyectoService.saveTalk(nuevoTalk);
			// emailService.mandaEmail(correo, "OPEN INFO SUPPORT",
			// "Su talk fue registrado exitosamente" + "\n" + "si fue aprobado nos
			// contactaremos con usted");

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al registrar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("miniTalk", nuevoTalk);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Busca proyectos por nombre de proyecto")
	@GetMapping("/buscarProyecto/{nombre}")
	public ResponseEntity<?> buscarUsuario(@PathVariable String nombre) {

		List<Proyecto> proyectoPorNombre = new ArrayList<Proyecto>();
		List<ProyectoBuscadorDTO> proyectoBuscador = new ArrayList<ProyectoBuscadorDTO>();
		Map<String, Object> response = new HashMap<>();
		Gestion gestion=new Gestion();
		try {
			gestion=proyectoService.findByHabilitadoTrue();
			proyectoPorNombre = proyectoService.findByNombreContainingAndGestion(nombre,gestion.getId());
			for (Proyecto pry : proyectoPorNombre) {
				ParticipanteProyecto representante = proyectoService.findByRepresentanteTrueAndProyecto_Id(pry.getId());
				Usuario repre = new Usuario();
				ProyectoBuscadorDTO proyectoBuscadorDTO = new ProyectoBuscadorDTO();
				if (representante != null) {
					repre = usuarioService.findById(representante.getParticipante().getUsuario().getCodRegistro());
					proyectoBuscadorDTO.setEncargado(repre);
				}
				proyectoBuscadorDTO.setId(pry.getId());
				proyectoBuscadorDTO.setNombre(pry.getNombre());
				proyectoBuscador.add(proyectoBuscadorDTO);
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("proyectos", proyectoBuscador);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Suma una vista a un video de un proyecto o talk por cada reproduccion")
	@PostMapping("/view")
	public ResponseEntity<?> registrarVista(@RequestBody RegistrarVistaDTO vistaDTO) {
		Map<String, Object> response = new HashMap<>();
		Proyecto nuevoProyecto = new Proyecto();
		MiniTalk talk = new MiniTalk();
		try {
			if (vistaDTO.getTipo().equals("talk")) {
				talk = proyectoService.findByIdTalk(vistaDTO.getId());
				if (talk != null) {
					talk.setVistas(talk.getVistas() + 1);
					proyectoService.saveTalk(talk);
					response.put("result", "cantidad de vistas actualizado con exito");
				} else {
					response.put("result", "no existe un talk con ese id");
				}

			} else if (vistaDTO.getTipo().equals("proyecto")) {
				nuevoProyecto = proyectoService.findById(vistaDTO.getId());
				if (nuevoProyecto != null) {
					nuevoProyecto.setVistas(nuevoProyecto.getVistas() + 1);
					proyectoService.save(nuevoProyecto);
					response.put("result", "cantidad de vistas actualizado con exito");
				} else {
					response.put("result", "no existe un proyecto con ese id");
				}

			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Listar proyectos de un jefe de actividad")
	@GetMapping("/proyectosUsuario/{codUsuario}")
	public ResponseEntity<?> proyectosJefeDeActividad(@PathVariable String codUsuario) {

		List<ParticipanteProyecto> participantesProyecto = new ArrayList<ParticipanteProyecto>();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		List<ProyectoDTO> listaProyectos = new ArrayList<ProyectoDTO>();
		Map<String, Object> response = new HashMap<>();
		Gestion gestion=new Gestion();
		try {
			gestion=proyectoService.findByHabilitadoTrue();
			participantesProyecto = proyectoService.proyectosJefeDeActividad(codUsuario,gestion.getId());
			for (ParticipanteProyecto pry : participantesProyecto) {
				Proyecto proyect = pry.getProyecto();

				ModelMapper mapper = new ModelMapper();
				ProyectoDTO proyectoDTO = mapper.map(proyect, ProyectoDTO.class);
				participantes = proyectoService.listaParticipantes(proyect.getId());
				List<Participante> particip = new ArrayList<Participante>();
				List<String> objetivosStrings = new ArrayList<String>();
				if (proyect.getObjetivosEspecificos() != null) {
					String[] objetivos = proyect.getObjetivosEspecificos().split("_");
					for (String obj : objetivos) {

						objetivosStrings.add(obj);

					}
				}

				for (ParticipanteProyecto parta : participantes) {
					Participante par = parta.getParticipante();
					particip.add(par);
				}
				List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
				for (Participante pa : particip) {
					ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
					participanteDTO.add(participante);
				}

				proyectoDTO.setParticipantes(participanteDTO);
				proyectoDTO.setObjetivosEspecificos(objetivosStrings);
				listaProyectos.add(proyectoDTO);
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("proyectos", listaProyectos);
		response.put("JefeDeActividadCodRegistro", codUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Listar proyectos de un jefe de equipo o docente")
	@GetMapping("/proyectosDocente/{codRegistro}")
	public ResponseEntity<?> proyectosDocente(@PathVariable String codRegistro) {
		Map<String, Object> response = new HashMap<>();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		List<ProyectoDTO> listaProyectos = new ArrayList<ProyectoDTO>();
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		Gestion gestion=new Gestion();
		try {
			gestion=proyectoService.findByHabilitadoTrue();
			proyectos = proyectoService.proyectosDocente(codRegistro,gestion.getId());
			for (Proyecto pry : proyectos) {
				ModelMapper mapper = new ModelMapper();
				ProyectoDTO proyectoDTO = mapper.map(pry, ProyectoDTO.class);
				participantes = proyectoService.listaParticipantes(pry.getId());
				List<Participante> particip = new ArrayList<Participante>();
				List<String> objetivosStrings = new ArrayList<String>();
				if (pry.getObjetivosEspecificos() != null) {
					String[] objetivos = pry.getObjetivosEspecificos().split("_");
					for (String obj : objetivos) {

						objetivosStrings.add(obj);

					}
				}

				for (ParticipanteProyecto parta : participantes) {
					Participante par = parta.getParticipante();
					particip.add(par);
				}
				List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
				for (Participante pa : particip) {
					ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
					participanteDTO.add(participante);
				}

				proyectoDTO.setParticipantes(participanteDTO);
				proyectoDTO.setObjetivosEspecificos(objetivosStrings);
				listaProyectos.add(proyectoDTO);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("Proyectos", listaProyectos);
		response.put("Docente codRegistro", codRegistro);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	@Operation(summary = "- Listar de todas las actividades(proyectos y talks) de la ultima gestion")
	@GetMapping("/listaActividades")
	public ResponseEntity<?> listaActividades() {

		Map<String, Object> response = new HashMap<>();
		Gestion gestion = new Gestion();
		List<Proyecto> proyectosFeria = new ArrayList<Proyecto>();
		List<Proyecto> proyectosConcurso = new ArrayList<Proyecto>();
		List<MiniTalk> talks = new ArrayList<MiniTalk>();
		try {
			gestion = proyectoService.findByHabilitadoTrue();
			proyectosFeria = proyectoService.listaDeProyectos(gestion.getId(), "feria");
			proyectosConcurso = proyectoService.listaDeProyectos(gestion.getId(), "concurso");
			talks = proyectoService.listaTalks(gestion.getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("miniTalks", talks);
		response.put("proyectosFeria", proyectosFeria);
		response.put("proyectosConcurso", proyectosConcurso);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Actualiza los datos de un proyecto (para el rol comision)")
	@PutMapping("/actualizarProyecto")
	public ResponseEntity<?> actualizarProyComision(@RequestBody ActualizarProyectoDTO proyectoDTO) {

		Map<String, Object> response = new HashMap<>();
		MiniTalk talk = new MiniTalk();
		Proyecto proyecto = new Proyecto();

		try {
			String area = proyectoDTO.getArea();
			if (area.equals("talk")) {
				talk = proyectoService.findByIdTalk(proyectoDTO.getIdProyecto());
				talk.setLinkOficial(proyectoDTO.getLinkOficial());
				talk.setDescripcion(proyectoDTO.getDescripcion());
				talk.setHabilitado(proyectoDTO.isHabilitado());
				proyectoService.saveTalk(talk);
				response.put("talk", talk);
			} else {
				proyecto = proyectoService.findById(proyectoDTO.getIdProyecto());
				proyecto.setLinkOficial(proyectoDTO.getLinkOficial());
				proyecto.setDescripcion(proyectoDTO.getDescripcion());
				proyecto.setHabilitado(proyectoDTO.isHabilitado());
				proyecto.setArea(proyectoDTO.getAreaProyecto());
				TipoProyecto tipo = proyectoService.findByIdTipo(proyectoDTO.getIdTipoProyecto());
				proyecto.setTipoProyecto(tipo);
				proyectoService.save(proyecto);
				response.put("proyecto", proyecto);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Listar proyectos filtrados por area (proyecto o feria) y por tipo de proyecto")
	@GetMapping("/proyectosPorTipo/{idTipoProyecto}/{area}")
	public ResponseEntity<?> listaProyectosPorTipo(@PathVariable Long idTipoProyecto, @PathVariable String area) {
		Map<String, Object> response = new HashMap<>();
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		List<ParticipanteProyecto> participantes = new ArrayList<ParticipanteProyecto>();
		try {
			Gestion gestion = proyectoService.findByHabilitadoTrue();
			proyectos = proyectoService.findByTipoProyecto_IdAndArea(idTipoProyecto, area, gestion.getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<ProyectoDTO> listaProyectos = new ArrayList<ProyectoDTO>();
		if (!(proyectos == null)) {
			for (Proyecto act : proyectos) {
				ModelMapper mapper = new ModelMapper();
				ProyectoDTO proyectoDTO = mapper.map(act, ProyectoDTO.class);
				participantes = proyectoService.listaParticipantes(act.getId());
				List<Participante> particip = new ArrayList<Participante>();
				List<String> objetivosStrings = new ArrayList<String>();
				if (act.getObjetivosEspecificos() != null) {
					String[] objetivos = act.getObjetivosEspecificos().split("_");
					for (String obj : objetivos) {

						objetivosStrings.add(obj);

					}
				}

				for (ParticipanteProyecto parta : participantes) {
					Participante par = parta.getParticipante();
					particip.add(par);
				}
				List<ParticipanteDTO> participanteDTO = new ArrayList<ParticipanteDTO>();
				for (Participante pa : particip) {
					ParticipanteDTO participante = mapper.map(pa, ParticipanteDTO.class);
					participanteDTO.add(participante);
				}

				proyectoDTO.setParticipantes(participanteDTO);
				proyectoDTO.setObjetivosEspecificos(objetivosStrings);
				listaProyectos.add(proyectoDTO);
			}
		}

		response.put("proyectos", listaProyectos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
