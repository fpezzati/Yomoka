package edu.pezzati.yo.offer.model;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(ObjectId arg0, JsonGenerator arg1, SerializerProvider arg2)
	    throws IOException, JsonProcessingException {
	if (arg0 == null) {
	    arg1.writeNull();
	} else {
	    arg1.writeString(arg0.toString());
	}
    }
}