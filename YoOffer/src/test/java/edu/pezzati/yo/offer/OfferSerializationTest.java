package edu.pezzati.yo.offer;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.model.OfferSerializer;

public class OfferSerializationTest {

    @Test
    public void serializeNullOffer() {
	Logger log = Logger.getLogger(getClass());
	Offer offer = new Offer();
	ObjectMapper objectMapper = new ObjectMapper();
	SimpleModule offerModule = new SimpleModule();
	offerModule.addSerializer(Offer.class, new OfferSerializer());
	objectMapper.registerModule(offerModule);
	String emptyOffer = "{}";
	try {
	    Assert.assertEquals(emptyOffer, objectMapper.writeValueAsString(offer));
	} catch (JsonProcessingException e) {
	    log.error(e);
	}
	Assert.fail();
    }
}
