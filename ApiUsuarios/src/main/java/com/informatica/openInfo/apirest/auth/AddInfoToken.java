package com.informatica.openInfo.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.informatica.openInfo.apirest.models.Usuario;
import com.informatica.openInfo.apirest.services.IUsuarioService;

@Component
public class AddInfoToken implements TokenEnhancer{

	
	@Autowired
	private IUsuarioService usuarioService;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioService.findById(authentication.getName());
		Map<String, Object> infoAdicional =new HashMap<>(); 
		infoAdicional.put("nombre",usuario.getNombre());
		infoAdicional.put("correo",usuario.getCorreo());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoAdicional);
		return accessToken;
	}

}
