package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.model.Offer;

public interface OfferService {

	Response create(Offer offer);

	Response read(ObjectId offerId);

	Response update(Offer offer);

}
