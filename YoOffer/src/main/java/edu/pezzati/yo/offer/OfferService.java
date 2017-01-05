package edu.pezzati.yo.offer;

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

import edu.pezzati.yo.offer.model.Offer;

@Path("/offer")
public interface OfferService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response create(Offer offer);

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response read(@PathParam("id") ObjectId offerId);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response update(Offer offer);

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response delete(@PathParam("id") ObjectId offerId);
}
