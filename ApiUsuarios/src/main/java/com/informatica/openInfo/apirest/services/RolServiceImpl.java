package com.informatica.openInfo.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informatica.openInfo.apirest.Dao.IRolDao;
import com.informatica.openInfo.apirest.models.Rol;

@Service
public class RolServiceImpl implements IRolService{

	@Autowired
	public IRolDao rolDao;
	
	@Override
	public List<Rol> findAll() {
		// TODO Auto-generated method stub
		return (List<Rol>) rolDao.findAll();
	}

	@Override
	public Rol findById(Long id) {
		// TODO Auto-generated method stub
		return rolDao.findById(id).orElse(null);
	}

	@Override
	public Rol save(Rol rol) {
		// TODO Auto-generated method stub
		return rolDao.save(rol);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		rolDao.deleteById(id);
	}

}
