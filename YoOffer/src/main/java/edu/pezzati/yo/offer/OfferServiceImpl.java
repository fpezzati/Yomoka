package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceImpl implements OfferService {

    private PersistenceService persistenceService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    public OfferServiceImpl(PersistenceService persistenceService) {
	this.persistenceService = persistenceService;
    }

    @Override
    public Response create(Offer offer) {
	try {
	    Offer createdOffer = persistenceService.create(offer);
	    return Response.status(200).entity(createdOffer).build();
	} catch (IllegalArgumentException iae) {
	    log.error("Invalid input", iae);
	    return Response.status(400).entity(iae.getMessage()).build();
	} catch (Exception e) {
	    log.error("Unexpected error", e);
	    return Response.status(500).entity(e.getMessage()).build();
	}
    }

    @Override
    public Response read(ObjectId offerId) {
	try {
	    Offer offer = persistenceService.read(offerId);
	    return Response.status(200).entity(offer).build();
	} catch (IllegalArgumentException iae) {
	    log.error("Invalid input", iae);
	    return Response.status(400).entity(iae.getMessage()).build();
	} catch (OfferNotFound onf) {
	    log.error("Offer not found", onf);
	    return Response.status(404).entity(onf.getMessage()).build();
	} catch (Exception e) {
	    log.error("Unexpected error", e);
	    return Response.status(500).entity(e.getMessage()).build();
	}
    }

    @Override
    public Response update(Offer offer) {
	try {
	    Offer updatedOffer = persistenceService.update(offer);
	    return Response.status(200).entity(updatedOffer).build();
	} catch (IllegalArgumentException iae) {
	    log.error("Invalid input", iae);
	    return Response.status(400).entity(iae.getMessage()).build();
	} catch (OfferNotFound onf) {
	    log.error("Offer not found", onf);
	    return Response.status(404).entity(onf.getMessage()).build();
	} catch (Exception e) {
	    log.error("Unexpected error", e);
	    return Response.status(500).entity(e.getMessage()).build();
	}
    }

    @Override
    public Response delete(ObjectId offerId) {
	try {
	    Offer deletedOffer = persistenceService.delete(offerId);
	    return Response.status(200).entity(deletedOffer).build();
	} catch (IllegalArgumentException iae) {
	    log.error("Invalid input", iae);
	    return Response.status(400).entity(iae.getMessage()).build();
	} catch (OfferNotFound onf) {
	    log.error("Offer not found", onf);
	    return Response.status(404).entity(onf.getMessage()).build();
	} catch (Exception e) {
	    log.error("Unexpected error", e);
	    return Response.status(500).entity(e.getMessage()).build();
	}
    }
}
