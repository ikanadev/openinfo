package com.informatica.openInfo.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.informatica.openInfo.apirest.Dto.AuspiciadoresDTO;
import com.informatica.openInfo.apirest.models.Auspiciador;
import com.informatica.openInfo.apirest.models.Gestion;
import com.informatica.openInfo.apirest.services.IAuspiciadorService;
import com.informatica.openInfo.apirest.services.IProyectoService;
import com.informatica.openInfo.apirest.services.IUploadFileService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/openInfo")
public class AuspiciadorRestController {
	
	@Autowired
	private IAuspiciadorService auspiciadorService;
	
	@Autowired
	private IUploadFileService fileService;
	
	@Autowired
	private IProyectoService proyectoService;
	
	@Operation(summary = "- Lista de todos los auspiciadores")
	@GetMapping("/auspiciadores")
	public ResponseEntity<?> listaAuspiciadores(){
		
		Map<String, Object> response = new HashMap<>();
		List<Auspiciador> auspiciadores = new ArrayList<Auspiciador>();
		List<AuspiciadoresDTO> auspiciadoress=new ArrayList<AuspiciadoresDTO>();
		try {
			auspiciadores = auspiciadorService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (auspiciadores.isEmpty()) {
			response.put("mensaje", "No existen contactos registrados");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		for(Auspiciador aus : auspiciadores) {
			
			ModelMapper modelMapper=new ModelMapper();
			AuspiciadoresDTO auspiciador= modelMapper.map(aus,AuspiciadoresDTO.class);
			auspiciadoress.add(auspiciador);
		}
		response.put("auspiciadores", auspiciadoress);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	@Operation(summary = "- Devuelve la imagen dado el nombre de la misma")
	@GetMapping("/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

		Resource recurso = null;
		
		try {
			recurso = fileService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@Operation(summary = "- Registra un nuevo auspiciador")
	@PostMapping("/auspiciadores")
	public ResponseEntity<?> registrarAuspiciador(@RequestParam("nombre") String nombre, @RequestParam("logo") MultipartFile logo,
			@RequestParam("descripcion") String descripcion, @RequestParam("contacto") String contacto, @RequestParam("linkPagina") String link){
		
		Auspiciador auspiciador=new Auspiciador();
		Map<String, Object> response = new HashMap<>();
		if(!logo.isEmpty()) {
			
			String nombreArchivo = null;
			
			try {
				Gestion gestion=proyectoService.findByHabilitadoTrue();
				nombreArchivo = fileService.copiar(logo);
				auspiciador.setNombre(nombre);
				auspiciador.setDescripcion(descripcion);
				auspiciador.setLogo(nombreArchivo);
				auspiciador.setContacto(contacto);
				auspiciador.setLink(link);
				auspiciador.setGestion(gestion);
				auspiciadorService.save(auspiciador);
				response.put("auspiciador", auspiciador);
				response.put("mensaje", "Has subido correctamente el logo: " + nombreArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del auspiciador");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}else {
			response.put("error","debe seleccionar un logo antes de registrar");
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
}
