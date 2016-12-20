package edu.pezzati.yo.offer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.model.Offer;

public class OfferSerializationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void serializeEmptyOffer() throws JsonProcessingException {
	Offer offer = new Offer();
	ObjectMapper objectMapper = new ObjectMapper();
	expectedException.expect(JsonMappingException.class);
	objectMapper.writeValueAsString(offer);
	Assert.fail();
    }

    public void serializeFullyValuedOffer() {
	Offer offer = new Offer();
	ObjectMapper objectMapper = new ObjectMapper();
    }
}
