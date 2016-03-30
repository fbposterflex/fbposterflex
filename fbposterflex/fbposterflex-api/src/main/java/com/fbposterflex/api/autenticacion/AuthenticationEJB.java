package com.fbposterflex.api.autenticacion;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import com.fbposterflex.api.exception.BusinessException;
import com.fbposterflex.api.exception.CredencialesInvalidasException;
import com.fbposterflex.api.repository.TokenEntity;
import com.fbposterflex.api.repository.TokenRepository;
import com.fbposterflex.api.repository.UsuarioEntity;
import com.fbposterflex.api.repository.UsuarioRepository;
import com.fbposterflex.api.usuarios.UsuarioDTO;
import com.fbposterflex.api.util.Crypto;

@Stateless
public class AuthenticationEJB {

	@Inject
	private TokenRepository tokenQuery;

	@Inject
	private UsuarioRepository usuarioRepository;

	public String login(UsuarioDTO usuarioDTO) {

		String userName = usuarioDTO.getUserName();
		String password = usuarioDTO.getPassword();

		if (userName == null || userName.length() == 0) {
			throw new BusinessException("El nombre de usuario es requerido");
		}
		if (password == null || password.length() == 0) {
			throw new BusinessException("El password es requerido");
		}

		boolean credencialesValidas = true;
		UsuarioEntity usuario = null;

		try {
			usuario = usuarioRepository.usuarioPorUsername(userName);
		} catch (NoResultException ex) {
			credencialesValidas = false;
		}

		String hashPassword = Crypto.hcmac(password);
		credencialesValidas = usuario.getPassword().equals(hashPassword);

		if (!credencialesValidas) {
			throw new CredencialesInvalidasException("Las credenciales son incorrectas");
		}

		if (!usuario.isActivo()) {
			throw new CredencialesInvalidasException("El usuario no se encuentra activo");
		}

		Date fechaCreacion = DateTime.now().toDate();
		String token = RandomStringUtils.randomAlphabetic(20);
		String hashToken = Crypto.hcmac(token);
		Integer duracionTokenHoras = Integer.parseInt(ConfiguracionConst.DURACION_TOKENS);

		Date fechaExpiracion = new DateTime(fechaCreacion).plus(duracionTokenHoras).toDate();

		TokenEntity tokenNuevo = new TokenEntity();
		tokenNuevo.setIdUsuario(usuario);
		tokenNuevo.setFechaCreacion(fechaCreacion);
		tokenNuevo.setFechaExpira(fechaExpiracion);
		tokenNuevo.setToken(hashToken);
		tokenNuevo.setActivo(true);
		tokenQuery.registrarToken(tokenNuevo);

		return token;
	}

	public void cerrarSesion(String token) {
		String hashToken = Crypto.hcmac(token);
		TokenEntity tokenEntity = null;
		try {
			tokenEntity = tokenQuery.consultarToken(hashToken);
			tokenEntity.setActivo(false);
			tokenQuery.actualizarToken(tokenEntity);
		} catch (NoResultException ex) {
			ex.printStackTrace();
		}
	}

}