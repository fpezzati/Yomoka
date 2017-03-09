package edu.pezzati.yo.offer.model.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.pezzati.yo.offer.model.Offer;

public interface DatabaseFeeder {

	public void init(String dbUri);

	public void saveOffer(Offer... offers) throws JsonProcessingException;

	public void reset();

	public void close();
}
