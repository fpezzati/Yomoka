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

import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferException;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.HandleServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Offer management endpoint.", description = "This endpoint gives CRUD functionalities about offer.")
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

    @ApiOperation(value = "Create a new offer.", response = Offer.class, notes = "Create a new offer.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Offer succesfully created.", response = Offer.class),
	    @ApiResponse(code = 400, message = "The given offer was invalid."),
	    @ApiResponse(code = 500, message = "Internal server error.") })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @HandleServiceException
    public Response create(Offer offer) {
	Offer createdOffer = persistenceService.create(offer);
	return Response.status(200).entity(createdOffer).build();
    }

    @ApiOperation(value = "Find an offer by the given id.", response = Offer.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Offer succesfully found.", response = Offer.class),
	    @ApiResponse(code = 404, message = "Offer not found."),
	    @ApiResponse(code = 500, message = "Internal server error.") })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @HandleServiceException
    public Response read(@PathParam("id") ObjectId offerId) throws OfferNotFound {
	Offer offer = persistenceService.read(offerId);
	return Response.status(200).entity(offer).build();
    }

    @ApiOperation(value = "Update an existing offer.", response = Offer.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Offer succesfully updated.", response = Offer.class),
	    @ApiResponse(code = 400, message = "The given offer was invalid."),
	    @ApiResponse(code = 404, message = "Offer not found."),
	    @ApiResponse(code = 500, message = "Internal server error.") })
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @HandleServiceException
    public Response update(Offer offer) throws OfferException {
	Offer updatedOffer = persistenceService.update(offer);
	return Response.status(200).entity(updatedOffer).build();
    }

    @ApiOperation(value = "Delete an offer by the given id.", response = Offer.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Offer succesfully deleted.", response = Offer.class),
	    @ApiResponse(code = 404, message = "Offer not found."),
	    @ApiResponse(code = 500, message = "Internal server error.") })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @HandleServiceException
    public Response delete(@PathParam("id") ObjectId offerId) throws OfferNotFound {
	Offer deletedOffer = persistenceService.delete(offerId);
	return Response.status(200).entity(deletedOffer).build();
    }

    @ApiOperation(value = "Grab some goods by changing offer's amount.", response = Offer.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Offer succesfully updated.", response = Offer.class),
	    @ApiResponse(code = 400, message = "The given offer was invalid or requested amount was higher than available."),
	    @ApiResponse(code = 404, message = "Offer not found."),
	    @ApiResponse(code = 500, message = "Internal server error.") })
    @PUT
    @Path("/pick")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @HandleServiceException
    public Response pick(Offer offer) throws OfferException {
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
