package edu.pezzati.yo.offer.model;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String persistenceUnitName;
    private static EntityManager entityManager;
    private Offer offer;

    @BeforeClass
    public static void init() {
	persistenceUnitName = "yotest";
	EntityManagerFactory entityMFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	entityManager = entityMFactory.createEntityManager();
    }

    @After
    public void destroy() {
	if (entityManager != null) {
	    entityManager.close();
	}
    }

    @Test
    public void createNullOffer() {
	offer = null;
	expectedException.expect(IllegalArgumentException.class);
	entityManager.persist(offer);
    }

    @Test
    public void createInvalidOffer() {
	Offer offer = new Offer();
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	Set<ConstraintViolation<Offer>> validate = validator.validate(offer);
	expectedException.expect(IllegalArgumentException.class);
	entityManager.persist(offer);
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
