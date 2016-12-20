package edu.pezzati.yo.offer.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OfferSerializer extends JsonSerializer<Offer> {

    @Override
    public void serialize(Offer arg0, JsonGenerator arg1, SerializerProvider arg2)
	    throws IOException, JsonProcessingException {
	arg1.writeString("{}");
    }
}
