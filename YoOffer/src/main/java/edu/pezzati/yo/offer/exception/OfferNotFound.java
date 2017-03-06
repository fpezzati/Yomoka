package edu.pezzati.yo.offer.exception;

import edu.pezzati.yo.offer.model.Offer;

public class OfferNotFound extends OfferException {

    private static final long serialVersionUID = 2522493901176264826L;

    public OfferNotFound(Offer offer) {
	super(offer);
    }
}
