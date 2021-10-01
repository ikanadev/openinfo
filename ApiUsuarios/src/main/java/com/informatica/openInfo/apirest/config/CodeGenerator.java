package com.informatica.openInfo.apirest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IMiniTalkDao;
import com.informatica.openInfo.apirest.Dao.IProyectoDao;
import com.informatica.openInfo.apirest.Dao.IUsuarioDao;
import com.informatica.openInfo.apirest.models.MiniTalk;
import com.informatica.openInfo.apirest.models.Proyecto;
import com.informatica.openInfo.apirest.models.Usuario;

@Service
public class CodeGenerator {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IProyectoDao proyectoDao;
	
	@Autowired
	private IMiniTalkDao miniTalkDao;
	
	private static final String ALIAS = "oinfuser";
	
	private static final String PROY = "PRY";
	
	private static final String TALK = "TALK";
	
	
	public String generarCodigoNuevo() {
		String codigo = "";
		boolean sw=true;
		int num=Integer.parseInt(usuarioDao.cantidadDeRegistros());
		while(sw) {
			num=num+1;
			codigo=ALIAS+"-"+num;
			Usuario user=usuarioDao.findById(codigo).orElse(null);
			if(user==null) {
				sw=false;
			}	
		}
		return codigo;		
	}
	
	public String generarCodigoProyectos() {
		String codigo = "";
		boolean sw=true;
		int num=Integer.parseInt(proyectoDao.cantidadDeRegistros());
		while(sw) {
			num=num+1;
			codigo=PROY+"-"+num;
			Proyecto pry=proyectoDao.findByCodigo(codigo).orElse(null);
			
			if(pry==null) {
				sw=false;
			}	
		}
		return codigo;		
	}
	
	public String generarCodigoTalks() {
		String codigo = "";
		boolean sw=true;
		int num=Integer.parseInt(proyectoDao.cantidadDeRegistros());
		while(sw) {
			num=num+1;
			codigo=TALK+"-"+num;
			MiniTalk talk=miniTalkDao.findByCodigo(codigo);
			if(talk==null) {
				sw=false;
			}	
		}
		return codigo;		
	}


}
