package edu.pezzati.yo.offer;

import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import edu.pezzati.yo.offer.exception.OfferException;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public interface OfferPersistenceService {

    public Offer create(Offer offer);

    public Offer read(ObjectId id) throws OfferNotFound;

    public Offer update(Offer offer) throws OfferException;

    public Offer delete(ObjectId offerId) throws OfferNotFound;

    public EntityManager getEntityManager();
}
