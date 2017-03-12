package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.InvalidOffer;
import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.HandleServiceException;

@Path("/offer")
public class OfferServiceImpl implements OfferService {

	private OfferPersistenceService persistenceService;

	public OfferServiceImpl() {
		// Keep this to be compliant with Resteasy.
	}

	@Inject
	public OfferServiceImpl(@PersistenceUnit(name = "yodb") OfferPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@HandleServiceException
	public Response create(Offer offer) {
		Offer createdOffer = persistenceService.create(offer);
		return Response.status(200).entity(createdOffer).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@HandleServiceException
	public Response read(@PathParam("id") ObjectId offerId) throws OfferNotFound {
		Offer offer = persistenceService.read(offerId);
		return Response.status(200).entity(offer).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@HandleServiceException
	public Response update(Offer offer) throws OfferNotFound, InvalidOffer {
		Offer updatedOffer = persistenceService.update(offer);
		return Response.status(200).entity(updatedOffer).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@HandleServiceException
	public Response delete(@PathParam("id") ObjectId offerId) throws OfferNotFound {
		Offer deletedOffer = persistenceService.delete(offerId);
		return Response.status(200).entity(deletedOffer).build();
	}

	@PUT
	@Path("/pick")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@HandleServiceException
	public Response pick(Offer offer) throws OfferNotFound, NotEnoughOfferElements, InvalidOffer {
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
