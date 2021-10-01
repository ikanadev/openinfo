package com.informatica.openInfo.apirest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IJuradoDao;
import com.informatica.openInfo.apirest.Dao.IJuradoProyecoDao;
import com.informatica.openInfo.apirest.models.Jurado;
import com.informatica.openInfo.apirest.models.JuradoProyectos;

@Service
public class JuradoServiceImpl implements IJuradoService{

	
	@Autowired
	private IJuradoDao juradoDao;
	
	@Autowired
	private IJuradoProyecoDao juradoProyectoDao;
	
	@Override
	public List<Jurado> findAll() {
		// TODO Auto-generated method stub
		return (List<Jurado>) juradoDao.findByOrderByIdAsc().orElse(null);
	}

	@Override
	public Jurado findById(Long codRegistro) {
		// TODO Auto-generated method stub
		return juradoDao.findById(codRegistro).orElse(null);
	}

	@Override
	public Jurado save(Jurado jurado) {
		// TODO Auto-generated method stub
		return juradoDao.save(jurado);
	}

	@Override
	public List<JuradoProyectos> findByProyecto_area(String area) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.findByProyecto_Area(area);
	}

	@Override
	public JuradoProyectos findByIdJuradoProyectos(Long id) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.findById(id).orElse(null);
	}

	@Override
	public List<JuradoProyectos> findByProyecto_Id(Long id) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.findByProyecto_Id(id);
	}

	@Override
	public JuradoProyectos registrarJurado(JuradoProyectos juradoProyectos) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.save(juradoProyectos);
	}

	@Override
	public List<JuradoProyectos> findByJurado_Id(Long id) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.findByJurado_Id(id);
	}

	@Override
	public Jurado findByCodRegistro(String codRegistro) {
		// TODO Auto-generated method stub
		return juradoDao.findByUsuario_CodRegistro(codRegistro).orElse(null);
	}

	@Override
	public JuradoProyectos findByProyecto_IdAndJurado_Usuario_CodRegistro(Long ProyectoId, String codRegistro) {
		// TODO Auto-generated method stub
		return juradoProyectoDao.findByProyecto_IdAndJurado_Usuario_CodRegistro(ProyectoId, codRegistro).orElse(null);
	}

}
