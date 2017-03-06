package edu.pezzati.yo.offer.exception;

import edu.pezzati.yo.offer.model.Offer;

public class OfferException extends Exception {

    private static final long serialVersionUID = -6809656008003477215L;
    private Offer offer;

    public OfferException(Offer offer) {
	this.offer = offer;
    }

    public Offer getOffer() {
	return offer;
    }
}
