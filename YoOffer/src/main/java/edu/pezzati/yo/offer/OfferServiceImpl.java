package edu.pezzati.yo.offer;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;

import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceImpl implements OfferService {

	private PersistenceService persistenceService;
	private Validator validator;

	@Inject
	public OfferServiceImpl(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public Response create(Offer offer) {
		try {
			offer = persistenceService.create(offer);
			evaluateValidation(validator.validate(offer));
			return Response.status(200).entity(offer).build();
		} catch (IllegalArgumentException iae) {
			return Response.status(400).entity("Offer is not valid: " + offer.toString()).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}

	private void evaluateValidation(Set<ConstraintViolation<Offer>> validationResult) throws IllegalArgumentException {
		if (validationResult.isEmpty())
			return;
		StringBuilder errorMessage = new StringBuilder();
		for (ConstraintViolation<Offer> violation : validationResult) {
			errorMessage.append(violation.getMessage());
		}
		throw new IllegalArgumentException(errorMessage.toString());
	}
}
