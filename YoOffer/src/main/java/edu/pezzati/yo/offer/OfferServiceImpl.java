package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceImpl implements OfferService {

    private PersistenceService persistenceService;

    @Inject
    public OfferServiceImpl(PersistenceService persistenceService) {
	this.persistenceService = persistenceService;
    }

    @Override
    public Response create(Offer offer) {
	try {
	    offer = persistenceService.create(offer);
	    return Response.status(200).entity(offer).build();
	} catch (IllegalArgumentException iae) {
	    return Response.status(400).entity("Offer is not valid: " + iae.getMessage()).build();
	} catch (Exception e) {
	    return Response.status(500).build();
	}
    }
}
