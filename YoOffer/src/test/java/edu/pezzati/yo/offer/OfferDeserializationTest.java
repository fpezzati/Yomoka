package edu.pezzati.yo.offer;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

public class OfferDeserializationTest {

    private ObjectMapper objectMapper;
    private ObjectId offerId;
    private ObjectId ownerId;
    private String offer;

    @Before
    public void init() {
	objectMapper = new ObjectMapper();
	offerId = new ObjectId();
	ownerId = new ObjectId();
	offer = null;
    }

    @Test
    public void deserializeEmptyOffer() throws IOException {
	offer = "{}";
	Offer expected = new Offer();
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserializeFullyValuedOffer() throws IOException {
	offer = "{\"id\":\"" + offerId.toString() + "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\""
		+ ownerId.toString() + "\",\"price\":10.0,\"lat\":10.0,\"lon\":10.0}";
	Offer expected = new Offer(offerId, "some title", "some desc", ownerId, 10D, 10D, 10D);
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserializePartiallyValuedOffer() throws IOException {
	offer = "{\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString() + "\"}";
	Offer expected = new Offer(null, "some title", "some desc", ownerId, null, null, null);
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }
}
