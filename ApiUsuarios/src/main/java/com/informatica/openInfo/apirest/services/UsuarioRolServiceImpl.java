package com.informatica.openInfo.apirest.services;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.informatica.openInfo.apirest.Dao.IRolDao;
import com.informatica.openInfo.apirest.Dao.IUsuarioRolDao;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.UsuarioRol;
import com.informatica.openInfo.apirest.models.embedKeys.UsuarioRolKey;


@Service
public class UsuarioRolServiceImpl implements IUsuarioRolService{
	
	
	
	@Autowired
	private IUsuarioRolDao usuarioRolDao;
	
	

	@Override
	public List<UsuarioRol> findAll() {
		// TODO Auto-generated method stub
		return (List<UsuarioRol>) usuarioRolDao.findAll();
	}

	
	@Override
	public List<UsuarioRol> buscarPorRol(Long idRol) {
		// TODO Auto-generated method stub
		return usuarioRolDao.findByRol_IdRolOrderByUsuario_CodRegistroAsc(idRol);
	}

	@Override
	public UsuarioRol save(UsuarioRol rol) {
		// TODO Auto-generated method stub
		return usuarioRolDao.save(rol);
	}

	@Override
	public void delete(UsuarioRol usuarioRol) {
		// TODO Auto-generated method stub
		usuarioRolDao.delete(usuarioRol);
	}

	@Override
	public UsuarioRol findById(UsuarioRolKey usuarioRolkey) {
		// TODO Auto-generated method stub
		return usuarioRolDao.findById(usuarioRolkey).orElse(null);
	}


	@Override
	public UsuarioRol buscarPorCodRegistroAndRol(String codRegistro, Long idRol) {
		// TODO Auto-generated method stub
		return usuarioRolDao.findByUsuario_CodRegistroAndRol_IdRol(codRegistro, idRol);
	}
	


}
