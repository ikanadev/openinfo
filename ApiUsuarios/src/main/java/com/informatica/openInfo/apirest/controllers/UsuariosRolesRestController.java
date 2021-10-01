package com.informatica.openInfo.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.informatica.openInfo.apirest.Dto.BuscarPorCodRegistroDTO;
import com.informatica.openInfo.apirest.Dto.CambiarPasswordDTO;
import com.informatica.openInfo.apirest.Dto.EspectadorDTO;
import com.informatica.openInfo.apirest.Dto.UsuarioComisionDTO;
import com.informatica.openInfo.apirest.Dto.UsuarioDTO;
import com.informatica.openInfo.apirest.config.CodeGenerator;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioActividadKey;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;
import com.informatica.openInfo.apirest.services.EmailService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IRolService;
import com.informatica.openInfo.apirest.services.IUsuarioRolService;
import com.informatica.openInfo.apirest.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class UsuariosRolesRestController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IUsuarioRolService usuarioRolService;
	
	@Autowired
	private IProyectoService actividadService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CodeGenerator codegenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * SERVICIOS CRUD USUARIOS
	 * 
	 */
	@GetMapping("/usuarios")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuarios.isEmpty()) {
			response.put("mensaje", "No existen contactos registrados");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("data", usuarios);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/usuarios/{codRegistro}")
	public ResponseEntity<?> findById(@PathVariable String codRegistro) {

		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioService.findById(codRegistro);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response.put("mensaje", "El usuario ID: ".concat(codRegistro).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("data", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/buscarUsuario/{nombre}")
	public ResponseEntity<?> buscarUsuario(@PathVariable String nombre) {

		List<Usuario> usuariosPorNombre = null;
		List<Usuario> usuariosPorCodigo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuariosPorNombre = usuarioService.buscarPorNombre(nombre);
			usuariosPorCodigo = usuarioService.buscarPorCodRegistro(nombre);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuariosPorNombre == null && usuariosPorCodigo ==null ) {
			response.put("mensaje", "El usuario ID: ".concat(nombre).concat(" no existe en la base de datos pruebe datos mas precisos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Map<String, Object> datosUsuario = new HashMap<>();
		for (Usuario usr : usuariosPorNombre) {
			datosUsuario.put("codRegistro", usr.getCodRegistro());
			datosUsuario.put("nombre",usr.getNombre());	
		}
		usuariosPorNombre.addAll(usuariosPorCodigo);
		List<Usuario> noRepeditos = usuariosPorNombre
				.stream()
				.distinct()
				.collect(Collectors.toList());
		response.put("usuarios", noRepeditos);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	public ResponseEntity<?> save(@RequestBody UsuarioDTO usuario) {

		Usuario nuevoUsuario = new Usuario();
		Map<String, Object> response = new HashMap<>();
		try {

			//nuevoUsuario = usuarioService.findByCorreo(usuario.getCorreo());
			//if (nuevoUsuario != null) {
			//	response.put("mensaje", "el correo ya se encuentra registrado");
			//} else {
				nuevoUsuario = usuarioService.findByNombre(usuario.getNombre());
				if (nuevoUsuario != null) {
					response.put("mensaje", "el nombre ya se encuentra registrado");
				}else {
					ModelMapper mapper=new ModelMapper();
					Usuario nuevo=mapper.map(usuario, Usuario.class);
					nuevo.setCodRegistro(codegenerator.generarCodigoNuevo());  
					nuevo.setPassword(passwordEncoder.encode(codegenerator.generarCodigoNuevo()));
					nuevo.setHabilitado(true);
					nuevoUsuario = usuarioService.save(nuevo);
					response.put("mensaje", "El usuario fue creado con exito");
					response.put("usuario", nuevoUsuario);
					String content="Su registro Fue exitoso "+"\n"+"Codigo de registro: "+nuevoUsuario.getCodRegistro()
					+"\n"+ "nombre: "+nuevoUsuario.getNombre()+"\n"+"email: "+nuevoUsuario.getCorreo();
					emailService.mandaEmail(nuevo.getCorreo(), "OPEN INFO SUPPORT", content);
				}
				
			//}
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- Asigna rol de comision a un usuario")
	@PostMapping("/asignarComision")
	public ResponseEntity<?> asignaRolComision(@RequestBody BuscarPorCodRegistroDTO codRegistro) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario=new Usuario();
		Rol rol = new Rol();
		UsuarioRol asigna = new UsuarioRol();
		try {
			usuario=usuarioService.findById(codRegistro.getCodRegistro());
			if(usuario==null) {
				response.put("mensaje", "no se encuentra el usuario registrado en el sistema");
			}else {
				rol=rolService.findById(Long.valueOf(2)); //rol comision
				UsuarioRolKey clave = new UsuarioRolKey();
				clave.setCodRegistro(codRegistro.getCodRegistro());
				clave.setIdRol(rol.getIdRol());
				if(usuarioRolService.findById(clave)==null) {
					asigna.setId(clave);
					asigna.setRol(rol);
					asigna.setUsuario(usuario);
					usuarioRolService.save(asigna);
					response.put("mensaje", "el rol fue asignado con exito");
					response.put("datos", asigna);
				}else {
					response.put("mensaje", "el usuario ya tiene asignado el rol comision");
				}
			}
		
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al asignar el rol de comision al usuario ");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- Lista de usuarios de rol comision")
	@GetMapping("/comision")
	public ResponseEntity<?> usuariosComision() {
		Map<String, Object> response = new HashMap<>();
		List<UsuarioRol> usuarioRol=new ArrayList<UsuarioRol>();
		List<UsuarioComisionDTO> usuarioComisionDTO=new ArrayList<UsuarioComisionDTO>();
		try {
			usuarioRol=usuarioRolService.buscarPorRol(Long.valueOf(2));
			for(UsuarioRol u:usuarioRol) {
				UsuarioComisionDTO usuario=new UsuarioComisionDTO();
				usuario.setCodRegistro(u.getId().getCodRegistro());
				usuario.setNombre(u.getUsuario().getNombre());
				usuario.setCorreo(u.getUsuario().getCorreo());
				usuarioComisionDTO.add(usuario);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("comision", usuarioComisionDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@Operation(summary = "- elimina el rol de comision de un usuario")
	@DeleteMapping("/comision/{codRegistro}")
	public ResponseEntity<?> deleteComision(@PathVariable String codRegistro) {
		Map<String, Object> response = new HashMap<>();

		try {
			UsuarioRol usuario=usuarioRolService.buscarPorCodRegistroAndRol(codRegistro, Long.valueOf(2));
			usuarioRolService.delete(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "el rol fue eliminado con exito del usuario: "+ codRegistro);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- reset al password de un usuario")
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody BuscarPorCodRegistroDTO codRegistro) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario=new Usuario();
		
		try {
			usuario=usuarioService.findById(codRegistro.getCodRegistro());
			usuario.setPassword(passwordEncoder.encode(usuario.getCodRegistro()));
			usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "el password fue reseteado con exito ");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "- cambiar el password de un usuario")
	@PostMapping("/cambiarPassword")
	public ResponseEntity<?> cambiarPassword(@RequestBody CambiarPasswordDTO cambiaPassword) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario=new Usuario();
		
		try {
			usuario=usuarioService.findById(cambiaPassword.getCodRegistro());
			usuario.setPassword(passwordEncoder.encode(cambiaPassword.getPassword()));
			usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "el password fue cambiado con exito ");
		//response.put("usuario", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Operation(summary = "- actualiza datos de un usuario")
	@PutMapping("/actualizarUsuario")
	public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioComisionDTO usuarioDTO) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario=new Usuario();
		
		try {
			usuario=usuarioService.findById(usuarioDTO.getCodRegistro());
			if(usuario==null) {
				response.put("mensaje", "el usuario no se encuentra registrado en la base de datos");
			}else {
				usuario.setNombre(usuarioDTO.getNombre());
				usuario.setCorreo(usuarioDTO.getCorreo());
				usuarioService.save(usuario);
				response.put("mensaje", "los datos fueron actualizados con exito ");
				response.put("usuario", usuario);
			}
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	

//	@PostMapping("/usuariosRoles/{codRegistro}/{rol}")
//	public UsuarioRol save(@PathVariable String codRegistro, @PathVariable Long rol) {
//		// TODO Auto-generated method stub
//		Usuario user = usuarioService.findById(codRegistro);
//		Rol role = rolService.findById(rol);
//		UsuarioRolKey clave = new UsuarioRolKey();
//		clave.setCodRegistro(codRegistro);
//		clave.setIdRol(rol);
//		UsuarioRol asigna = new UsuarioRol();
//		asigna.setId(clave);
//		asigna.setRol(role);
//		asigna.setUsuario(user);
//		return usuarioRolService.save(asigna);
//	}

	/*
	 * registro espectador actividad
	 * 
	 */
	
//	@PostMapping("/registroEspectador")
//	public ResponseEntity<?> registroEspectador(@RequestBody EspectadorDTO espectador) {
//		// TODO Auto-generated method stub
//		Map<String, Object> response = new HashMap<>();	
//		Usuario user=new Usuario();
//		Proyecto actividad=new Proyecto();
//		UsuarioActividad usuarioActividad =new UsuarioActividad();
//		try {
//			user = usuarioService.findById(espectador.getCodRegistro());
//			actividad=actividadService.findById(espectador.getIdActividad());
//		} catch (DataAccessException e) {
//			response.put("mensaje", "Error al insertar en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		if (user == null) {
//			response.put("error", "codigo de registro incorrecto");
//		} else {
//			UsuarioActividadKey key=new UsuarioActividadKey();
//			key.setCodRegistro(user.getCodRegistro());
//			key.setIdActividad(actividad.getIdActividad());
//			usuarioActividad.setId(key);
//			usuarioActividad.setActividad(actividad);
//			usuarioActividad.setUsuario(user);
//			usuarioService.registroActividad(usuarioActividad);
//			response.put("mensaje", "El registro fue exitoso le enviamos el detalle a su correo");
//			String content="registro exitoso a "+ actividad.getTitulo();
//			emailService.mandaEmail(user.getCorreo(), "Open Info Support", content);
//			response.put("usuario", user.getCodRegistro());
//			response.put("actividad", actividad.getTitulo());
//		}
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//	}


	
}
