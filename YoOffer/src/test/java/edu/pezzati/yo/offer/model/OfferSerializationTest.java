package edu.pezzati.yo.offer.model;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

/**
 * OfferSerializationTest check that Offer will be serialized as expected when
 * passed by RESTful service. For example when frontend requests for an Offer.
 * 
 * @author Francesco
 *
 */
public class OfferSerializationTest {

    private ObjectId ownerId;

    private Offer offer;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
	ownerId = new ObjectId();
	objectMapper = new ObjectMapper();
    }

    @Test
    public void serializeNullOffer() throws JsonProcessingException {
	offer = null;
	String expected = "null";
	String actual = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void serializeEmptyOffer() throws JsonProcessingException {
	offer = new Offer();
	String expected = "{}";
	String actual = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void serializeFullyValuedOffer() throws JsonProcessingException {
	ObjectId offerId = new ObjectId();
	offer = new Offer(offerId, "some title", "some desc", ownerId, 10D, 10D, 10D);
	String expected = "{\"id\":\"" + offerId.toString()
		+ "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\",\"price\":10.0,\"lat\":10.0,\"lon\":10.0}";
	String actual = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void serializePartiallyValuedOffer() throws JsonProcessingException {
	offer = new Offer(null, "some title", "some desc", ownerId, null, null, null);
	String expected = "{\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString()
		+ "\"}";
	String actual = objectMapper.writeValueAsString(offer);
	Assert.assertEquals(expected, actual);
    }
}
