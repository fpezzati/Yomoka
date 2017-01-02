package edu.pezzati.yo.offer.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OfferPersistenceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void createNullOffer() {
		String persistenceUnitName = "yotest";
		EntityManagerFactory entityMFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		EntityManager entityManager = entityMFactory.createEntityManager();
		Offer offer = null;
		expectedException.expect(NullPointerException.class);
		entityManager.persist(offer);
	}

	public void createInvalidOffer() {

	}

	public void createOffer() {

	}

	public void readOffer() {

	}

	public void updateOffer() {

	}

	public void deleteOffer() {

	}
}
