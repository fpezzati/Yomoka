package edu.pezzati.yo.offer;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

public class OfferSerializationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void serializeEmptyOffer() throws JsonProcessingException {
	Offer offer = new Offer();
	ObjectMapper objectMapper = new ObjectMapper();
	String expectedOffer = "{}";
	String actualOffer = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void serializeFullyValuedOffer() throws JsonProcessingException {
	ObjectId offerId = new ObjectId();
	ObjectId ownerId = new ObjectId();
	Offer offer = new Offer(offerId, "some title", "some desc", ownerId, 10D, 10D, 10D);
	String expectedOffer = "{\"id\":\"" + offerId.toString()
		+ "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\",\"price\":10.0,\"lat\":10.0,\"lon\":10.0}";
	ObjectMapper objectMapper = new ObjectMapper();
	String actualOffer = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expectedOffer, actualOffer);
    }
}
