package edu.pezzati.yo.offer.exception;

import edu.pezzati.yo.offer.model.Offer;

public class IllegalOffer extends OfferException {

    private static final long serialVersionUID = -6928662464387518361L;

    public IllegalOffer(Offer offer) {
	super(offer);
    }
}
