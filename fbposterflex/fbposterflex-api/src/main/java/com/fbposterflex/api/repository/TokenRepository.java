package com.fbposterflex.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TokenRepository {

	@PersistenceContext(unitName = "fbposterflex")
	private EntityManager entityManager;

	public void registrarToken(TokenEntity token) {
		entityManager.persist(token);
	}

	public TokenEntity consultarToken(String token) {
		TokenEntity tokenEntity = entityManager
				.createQuery("FROM TokenEntity a WHERE a.token=:token", TokenEntity.class).setParameter("token", token)
				.getSingleResult();
		return tokenEntity;
	}

	public void actualizarToken(TokenEntity token) {
		entityManager.merge(token);
	}

}