package edu.pezzati.yo.offer.model;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Because Offer will be moved from frontend to backend,
 * OfferDeserializationTest check that Offer will be correctly deserialized when
 * received by RESTful service.
 * 
 * @author Francesco
 *
 */
public class OfferDeserializationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void deserializeNullOffer() throws IOException {
	offer = null;
	expectedException.expect(NullPointerException.class);
	objectMapper.readValue(offer, Offer.class);
    }

    @Test
    public void deserializeEmptyStringOffer() throws IOException {
	offer = "";
	expectedException.expect(JsonMappingException.class);
	objectMapper.readValue(offer, Offer.class);
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
	offer = "{\"_id\":\"" + offerId.toString() + "\",\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\""
		+ ownerId.toString() + "\",\"price\":10.0,\"amount\":5,\"lat\":10.0,\"lon\":10.0}";
	Offer expected = new Offer(offerId, "some title", "some desc", ownerId, 10D, 5, 10D, 10D);
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserializePartiallyValuedOffer() throws IOException {
	offer = "{\"title\":\"some title\",\"desc\":\"some desc\",\"ownerId\":\"" + ownerId.toString() + "\"}";
	Offer expected = new Offer(null, "some title", "some desc", ownerId, null, null, null, null);
	Offer actual = objectMapper.readValue(offer, Offer.class);
	Assert.assertEquals(expected, actual);
    }
}
