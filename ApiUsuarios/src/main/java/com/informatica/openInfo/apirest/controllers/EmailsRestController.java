package com.informatica.openInfo.apirest.controllers;

import java.util.HashMap;
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

import com.informatica.openInfo.apirest.Dto.EmailDTO;
import com.informatica.openInfo.apirest.Dto.EspectadorDTO;
import com.informatica.openInfo.apirest.Dto.UsuarioDTO;
import com.informatica.openInfo.apirest.models.Jurado;
import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.services.EmailService;
import com.informatica.openInfo.apirest.services.IJuradoService;
import com.informatica.openInfo.apirest.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class EmailsRestController {
	
	//@Autowired
	//private EmailService emailService;
	
	@Autowired
	private IUsuarioService usuarioservice;
	
	@Autowired
	private IJuradoService juradoService;
	
	@Operation(summary = "- Envia consultas al correo registrado en el sistema")
	@PostMapping("/contacto")
	public ResponseEntity<?> enviarMensaje(@RequestBody EmailDTO contacto){
		Map<String, Object> response = new HashMap<>();	
		String contenido="Mensaje de: "+contacto.getNombre()+"\n"+ "correo remitente: "+contacto.getCorreo()+"\n"+"contenido: "+contacto.getContenido();
		//emailService.sendEmail(contacto.getCorreo(),"ceti.lin.umsa@gmail.com", contacto.getNombre(), contenido);
		response.put("mensaje", "el correo fue enviado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	
	/*
	 * SERVICIOS JURADOS
	 * 
	 * */

	
	@Operation(summary = "- busca un jurado por carnet")
	@GetMapping("/listaJurados/{codRegistro}")
	@Secured({ "ROLE_ADMIN", "ROLE_COMISION" })
	public ResponseEntity<?> juradoporId(@PathVariable String codRegistro) {

		Jurado nuevoJurado = new Jurado();
		Map<String, Object> response = new HashMap<>();
		try {

			//nuevoJurado = juradoService.findById(codRegistro);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (nuevoJurado != null) {
			response.put("error", "el jurado ya se encuentra asignado");
		} else {
			ModelMapper mapper=new ModelMapper();
			//Jurado nuevo=mapper.map(jurado, Jurado.class);
			//nuevo.setTipoProyecto(jurado.getIdTipoProyecto());
		//	nuevoJurado = juradoService.save(nuevo);
			response.put("mensaje", "El jurado fue asignado con exito");
			response.put("usuario", nuevoJurado);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


}
