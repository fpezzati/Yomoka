package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.OfferException;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public interface OfferService {

    Response create(Offer offer);

    Response read(ObjectId offerId) throws OfferNotFound;

    Response update(Offer offer) throws OfferException;

    Response delete(ObjectId offerId) throws OfferNotFound;

    Response pick(Offer offer) throws OfferException;
}
