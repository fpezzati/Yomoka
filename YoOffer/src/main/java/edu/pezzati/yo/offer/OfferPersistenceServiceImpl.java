package edu.pezzati.yo.offer;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.InvalidOffer;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

@Default
@ApplicationScoped
public class OfferPersistenceServiceImpl implements OfferPersistenceService {

	private Validator validator;

	private EntityManager entityM;

	public OfferPersistenceServiceImpl(String persistenceUnitName) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		EntityManagerFactory entityMFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		entityM = entityMFactory.createEntityManager();
	}

	@Override
	public Offer create(Offer offer) {
		evaluateValidation(validator.validate(offer));
		EntityTransaction transaction = entityM.getTransaction();
		try {
			transaction.begin();
			entityM.persist(offer);
			entityM.refresh(offer);
			transaction.commit();
			return offer;
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public Offer read(ObjectId id) throws OfferNotFound {
		Offer offer = entityM.find(Offer.class, id);
		if (offer == null) {
			throw new OfferNotFound();
		} else {
			return offer;
		}
	}

	@Override
	public Offer update(Offer offer) throws OfferNotFound, InvalidOffer {
		if (offer.getId() == null)
			throw new InvalidOffer("Invalid offer: " + offer.toString());
		evaluateValidation(validator.validate(offer));
		EntityTransaction transaction = entityM.getTransaction();
		try {
			transaction.begin();
			Offer foundOffer = entityM.find(Offer.class, offer.getId());
			if (foundOffer == null)
				throw new OfferNotFound();
			entityM.merge(offer);
			entityM.refresh(offer);
			transaction.commit();
			return offer;
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public Offer delete(ObjectId offerId) throws OfferNotFound {
		if (offerId == null) {
			throw new IllegalArgumentException();
		}
		EntityTransaction transaction = entityM.getTransaction();
		try {
			transaction.begin();
			Offer offer = entityM.find(Offer.class, offerId);
			if (offer == null)
				throw new OfferNotFound();
			entityM.remove(offer);
			transaction.commit();
			return offer;
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	private void evaluateValidation(Set<ConstraintViolation<Offer>> validationResult) {
		if (validationResult.isEmpty())
			return;
		StringBuilder errorMessage = new StringBuilder();
		for (ConstraintViolation<Offer> violation : validationResult) {
			errorMessage.append(violation.getMessage());
			errorMessage.append(", ");
		}
		throw new IllegalArgumentException(errorMessage.toString());
	}
}
