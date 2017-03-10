package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.InvalidOffer;
import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public interface OfferService {

	Response create(Offer offer);

	Response read(ObjectId offerId) throws OfferNotFound;

	Response update(Offer offer) throws OfferNotFound, InvalidOffer;

	Response delete(ObjectId offerId) throws OfferNotFound;

	Response pick(Offer offer) throws OfferNotFound, NotEnoughOfferElements, InvalidOffer;
}
