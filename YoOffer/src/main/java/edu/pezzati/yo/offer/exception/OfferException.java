package edu.pezzati.yo.offer.exception;

import edu.pezzati.yo.offer.model.Offer;

public class OfferException extends Exception {

    private static final long serialVersionUID = -6809656008003477215L;
    private transient Offer offer;

    public OfferException(String message) {
	super(message);
    }

    public OfferException() {
	super();
    }

    public void setOffer(Offer offer) {
	this.offer = offer;
    }

    public Offer getOffer() {
	return offer;
    }
}
