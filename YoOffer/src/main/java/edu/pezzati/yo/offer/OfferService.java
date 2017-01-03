package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import edu.pezzati.yo.offer.model.Offer;

public interface OfferService {

	Response create(Offer offer);

}
