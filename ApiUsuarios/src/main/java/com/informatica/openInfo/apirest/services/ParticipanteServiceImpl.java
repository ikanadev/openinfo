package com.informatica.openInfo.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IParticipanteDao;
import com.informatica.openInfo.apirest.models.Participante;

@Service
public class ParticipanteServiceImpl implements IParticipanteService{

	
	@Autowired
	private IParticipanteDao participanteDao;
	
	@Override
	public List<Participante> findAll() {
		// TODO Auto-generated method stub
		return (List<Participante>) participanteDao.findAll();
	}

	@Override
	public Participante findById(Long idParticipante) {
		// TODO Auto-generated method stub
		return participanteDao.findById(idParticipante).orElse(null);
	}

	@Override
	public Participante save(Participante participante) {
		// TODO Auto-generated method stub
		return participanteDao.save(participante);
	}

	@Override
	public void delete(Long idParticipante) {
		// TODO Auto-generated method stub
		participanteDao.deleteById(idParticipante);
	}

	@Override
	public List<Participante> listaParticipantes() {
		// TODO Auto-generated method stub
		return participanteDao.listaDeParticipantes();
	}


	@Override
	public List<Participante> listarPorActividad(Long idActividad) {
		// TODO Auto-generated method stub
		return (List<Participante>)participanteDao.listarPorActividad(idActividad);
	}

	@Override
	public List<Participante> listarPorEquipo(Long idEquipo) {
		// TODO Auto-generated method stub
		return participanteDao.listarPorEquipo(idEquipo);
	}

	@Override
	public Participante findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return participanteDao.findByUsuario_Nombre(nombre);
	}

	@Override
	public Participante findByUsuario_CodRegistro(String codRegistro) {
		// TODO Auto-generated method stub
		return participanteDao.findByUsuario_CodRegistro(codRegistro).orElse(null);
	}

}
