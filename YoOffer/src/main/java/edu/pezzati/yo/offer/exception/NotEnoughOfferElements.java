package edu.pezzati.yo.offer.exception;

import edu.pezzati.yo.offer.model.Offer;

public class NotEnoughOfferElements extends OfferException {

    private static final long serialVersionUID = 2974571257496673674L;

    public NotEnoughOfferElements(Offer offer) {
	super(offer);
    }
}
