package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceImpl implements OfferService {

	@Override
	public Response create(Offer offer) {
		return Response.status(400).build();
	}
}
