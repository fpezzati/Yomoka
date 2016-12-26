package edu.pezzati.yo.offer;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

public class OfferDeserializationTest {

    @Test
    public void deserializeEmptyOffer() throws IOException {
	String offer = "{}";
	ObjectMapper objectMapper = new ObjectMapper();
	Offer expectedOffer = new Offer();
	Offer actualOffer = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void deserializeFullyValuedOffer() throws IOException {
	ObjectId offerId = new ObjectId();
	ObjectId ownerId = new ObjectId();
	String offer = "{\"id\":\"" + offerId.toString()
		+ "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\",\"price\":10.0,\"lat\":10.0,\"lon\":10.0}";
	ObjectMapper objectMapper = new ObjectMapper();
	Offer expectedOffer = new Offer(offerId, "some title", "some desc", ownerId, 10D, 10D, 10D);
	Offer actualOffer = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void deserializePartiallyValuedOffer() throws IOException {
	ObjectId ownerId = new ObjectId();
	String offer = "{\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString() + "\"}";
	ObjectMapper objectMapper = new ObjectMapper();
	Offer expected = new Offer(null, "some title", "some desc", ownerId, null, null, null);
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }
}
