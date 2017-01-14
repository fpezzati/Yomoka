package edu.pezzati.yo.offer;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public interface OfferPersistenceService {

    public Offer create(Offer offer);

    public Offer read(ObjectId id) throws OfferNotFound;

    public Offer update(Offer offer) throws OfferNotFound;

    public Offer delete(ObjectId offerId) throws OfferNotFound;
}
