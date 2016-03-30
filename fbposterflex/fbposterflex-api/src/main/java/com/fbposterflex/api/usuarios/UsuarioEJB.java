package com.fbposterflex.api.usuarios;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fbposterflex.api.repository.TokenRepository;
import com.fbposterflex.api.repository.UsuarioEntity;
import com.fbposterflex.api.repository.UsuarioRepository;

@Stateless
public class UsuarioEJB implements Serializable {

	private static final long serialVersionUID = -5675711499122080946L;

	@Inject
	UsuarioRepository usuarioRepository;

	@Inject
	TokenRepository tokenRepository;

	public UsuarioDTO usuario(UsuarioEntity usuario) {

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(usuario.getIdUsuario());
		usuarioDTO.setUserName(usuario.getUserName());
		usuarioDTO.setPassword(usuario.getPassword());
		usuarioDTO.setActivo(usuario.isActivo());
		usuarioDTO.setNombre(usuario.getNombre());
		usuarioDTO.setCorreo(usuario.getCorreo());
		usuarioDTO.setFechaAlta(usuario.getFechaAlta());
		return usuarioDTO;
	}

}