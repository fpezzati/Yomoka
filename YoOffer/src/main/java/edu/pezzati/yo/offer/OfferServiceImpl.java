package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.HandleServiceException;

public class OfferServiceImpl implements OfferService {

	private OfferPersistenceService persistenceService;

	@Inject
	public OfferServiceImpl(@PersistenceUnit(name = "yodb") OfferPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	@HandleServiceException
	public Response create(Offer offer) {
		Offer createdOffer = persistenceService.create(offer);
		return Response.status(200).entity(createdOffer).build();
	}

	@Override
	@HandleServiceException
	public Response read(ObjectId offerId) throws OfferNotFound {
		Offer offer = persistenceService.read(offerId);
		return Response.status(200).entity(offer).build();
	}

	@Override
	@HandleServiceException
	public Response update(Offer offer) throws OfferNotFound {
		Offer updatedOffer = persistenceService.update(offer);
		return Response.status(200).entity(updatedOffer).build();
	}

	@Override
	@HandleServiceException
	public Response delete(ObjectId offerId) throws OfferNotFound {
		Offer deletedOffer = persistenceService.delete(offerId);
		return Response.status(200).entity(deletedOffer).build();
	}

	@Override
	@HandleServiceException
	public Response pick(Offer offer) throws OfferNotFound, NotEnoughOfferElements {
		Offer offerToPick = persistenceService.read(offer.getId());
		if (offer.canPick(offerToPick)) {
			offerToPick.setAmount(offerToPick.getAmount() - offer.getAmount());
			persistenceService.update(offerToPick);
		} else {
			throw new NotEnoughOfferElements();
		}
		return Response.status(200).entity(offer).build();
	}
}
