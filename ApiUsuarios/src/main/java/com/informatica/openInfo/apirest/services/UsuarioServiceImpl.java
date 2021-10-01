package com.informatica.openInfo.apirest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.informatica.openInfo.apirest.Dao.IRolDao;
import com.informatica.openInfo.apirest.Dao.IUsuarioDao;
import com.informatica.openInfo.apirest.models.Rol;
import com.informatica.openInfo.apirest.models.Usuario;


@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService{
	
	@Autowired
	public IUsuarioDao usuarioDao;
	
	@Autowired
	public IRolDao rolDao;
	

	private Logger logger = org.slf4j.LoggerFactory.getLogger(UsuarioRolServiceImpl.class);
	
	@Override
	public List<Usuario> findAll() {
		
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	public Usuario findById(String codRegistro) {
		
		return usuarioDao.findById(codRegistro).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(String id) {
		
		usuarioDao.deleteById(id);
		
	}
	
	@Override
	public Usuario findByCorreo(String correo) {
		
		return usuarioDao.findByCorreo(correo);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				Usuario usuario = usuarioDao.findById(username).orElse(null);
				if(usuario == null) {
					logger.error("Error de login: No existe el usuario"+username+ " en el sistema");
					throw new UsernameNotFoundException("Error de login: No existe el usuario"+username+ " en el sistema");
				}
				List<Rol> rolesUser= rolDao.rolesDeUsuario(usuario.getCodRegistro());
				List<GrantedAuthority> authorities= rolesUser.stream()
						.map(role -> new SimpleGrantedAuthority(role.getTipo()))
						.peek(authority ->   logger.info("Role: "+authority.getAuthority()))
						.collect(Collectors.toList());
				
				return new User(usuario.getCodRegistro(), usuario.getPassword(), usuario.isHabilitado(), true, true, true, authorities);
	}

	@Override
	public List<Usuario> buscarPorNombre(String nombre) {
		
		return (List<Usuario>)usuarioDao.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public List<Usuario> buscarPorCodRegistro(String codRegistro) {
		// TODO Auto-generated method stub
		return usuarioDao.findByCodRegistroContainingIgnoreCase(codRegistro);
	}

	@Override
	public String cantidadDeRegistros() {
		// TODO Auto-generated method stub
		return usuarioDao.cantidadDeRegistros();
	}

	@Override
	public Usuario findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return usuarioDao.findByNombre(nombre);
	}

	

}
