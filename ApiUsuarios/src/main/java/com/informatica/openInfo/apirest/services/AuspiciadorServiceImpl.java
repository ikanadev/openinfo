package com.informatica.openInfo.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IAuspiciadorDao;
import com.informatica.openInfo.apirest.models.Auspiciador;

@Service
public class AuspiciadorServiceImpl implements IAuspiciadorService{

	
	@Autowired
	private IAuspiciadorDao auspiciadorDao;
	
	@Override
	public List<Auspiciador> findAll() {
		// TODO Auto-generated method stub
		return (List<Auspiciador>) auspiciadorDao.findAll();
	}

	@Override
	public Auspiciador findById(Long id) {
		// TODO Auto-generated method stub
		return auspiciadorDao.findById(id).orElse(null);
	}

	@Override
	public Auspiciador findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return auspiciadorDao.findByNombre(nombre);
	}

	@Override
	public Auspiciador save(Auspiciador auspiciador) {
		// TODO Auto-generated method stub
		return auspiciadorDao.save(auspiciador);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		auspiciadorDao.deleteById(id);
	}

}
