package edu.pezzati.yo.offer;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

public class OfferSerializationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ObjectId ownerId;

    private Offer offer;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
	ownerId = new ObjectId();
	objectMapper = new ObjectMapper();
    }

    @Test
    public void serializeEmptyOffer() throws JsonProcessingException {
	offer = new Offer();
	String expectedOffer = "{}";
	String actualOffer = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void serializeFullyValuedOffer() throws JsonProcessingException {
	ObjectId offerId = new ObjectId();
	offer = new Offer(offerId, "some title", "some desc", ownerId, 10D, 10D, 10D);
	String expectedOffer = "{\"id\":\"" + offerId.toString()
		+ "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\",\"price\":10.0,\"lat\":10.0,\"lon\":10.0}";
	String actualOffer = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void serializePartiallyValuedOffer() throws JsonProcessingException {
	offer = new Offer(null, "some title", "some desc", ownerId, null, null, null);
	String expectedOffer = "{\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\"}";
	String actualOffer = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expectedOffer, actualOffer);
    }
}
