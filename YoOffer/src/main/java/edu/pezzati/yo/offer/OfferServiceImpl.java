package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.OfferNotFound;
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
			return Response.status(400).entity(iae.getMessage()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response read(ObjectId offerId) {
		try {
			Offer offer = persistenceService.read(offerId);
			return Response.status(200).entity(offer).build();
		} catch (IllegalArgumentException iae) {
			return Response.status(400).entity(iae.getMessage()).build();
		} catch (OfferNotFound onf) {
			return Response.status(404).entity("Offer not found: ").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response update(Offer offer) {
		try {
			offer = persistenceService.update(offer);
			return Response.status(200).entity(offer).build();
		} catch (IllegalArgumentException iae) {
			return Response.status(400).entity(iae.getMessage()).build();
		} catch (OfferNotFound onf) {
			return Response.status(404).entity("Offer not found: ").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
