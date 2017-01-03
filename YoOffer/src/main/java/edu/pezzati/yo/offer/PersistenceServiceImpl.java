package edu.pezzati.yo.offer;

import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class PersistenceServiceImpl implements PersistenceService {

    private Validator validator;
    private EntityManager entityM;
    private static PersistenceServiceImpl instance;

    private PersistenceServiceImpl(String persistenceUnitName) {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	validator = factory.getValidator();
	EntityManagerFactory entityMFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	entityM = entityMFactory.createEntityManager();
    }

    @Inject
    public static PersistenceService getInstance(String persistenceUnitName) {
	if (instance == null) {
	    instance = new PersistenceServiceImpl(persistenceUnitName);
	}
	return instance;
    }

    @Override
    public <T> T create(T entity) throws IllegalArgumentException {
	evaluateValidation(validator.validate(entity));
	entityM.persist(entity);
	entityM.flush();
	entityM.refresh(entity);
	return entity;
    }

    private <T> void evaluateValidation(Set<ConstraintViolation<T>> validationResult) throws IllegalArgumentException {
	if (validationResult.isEmpty())
	    return;
	StringBuilder errorMessage = new StringBuilder();
	for (ConstraintViolation<T> violation : validationResult) {
	    errorMessage.append(violation.getMessage());
	}
	throw new IllegalArgumentException(errorMessage.toString());
    }
}
