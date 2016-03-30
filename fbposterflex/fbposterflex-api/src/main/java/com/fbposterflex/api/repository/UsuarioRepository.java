package com.fbposterflex.api.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 2519772084802925292L;

	@PersistenceContext(unitName = "fbposterflex")
	EntityManager entityManager;

	/**
	 * Registra un nuevo usuario
	 * 
	 * @param usuario
	 */
	public void registrarUsuario(UsuarioEntity usuario) {
		entityManager.persist(usuario);
	}

	/**
	 * Consulta la existencia de un usuario por su Id
	 * 
	 * @param idUsuario
	 * @return
	 */
	public UsuarioEntity consultarUsuarioPorId(Integer idUsuario) {
		return entityManager.find(UsuarioEntity.class, idUsuario);
	}

	/**
	 * Consulta si existe un usuario con el username indicado.
	 * 
	 * @param username
	 */
	public UsuarioEntity usuarioPorUsername(String userName) {
		try {
			return entityManager.createQuery("FROM UsuarioEntity u WHERE u.userName =:userName", UsuarioEntity.class).setParameter("userName", userName).getSingleResult();

		} catch (NoResultException exception) {
			return null;
		}

	}
	
}