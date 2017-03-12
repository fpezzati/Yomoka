package edu.pezzati.yo.offer.model.util;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;

import edu.pezzati.yo.offer.model.Offer;

public class FongoDatabaseFeeder implements DatabaseFeeder {

	private Fongo fongo;
	private DB db;
	private DBCollection offerCollection;
	private ObjectMapper objectMapper;

	public void init() {
		fongo = new Fongo("test");
		db = fongo.getDB("test");
		offerCollection = db.getCollection("offer");
		objectMapper = new ObjectMapper();
	}

	@Override
	public void init(String dbUri) {
		init();
	}

	@Override
	public void saveOffer(Offer... offers) throws JsonProcessingException {
		for (Offer offer : offers) {
			offer.setId(null);
			BasicDBObject jsonDoc = (BasicDBObject) JSON.parse(objectMapper.writeValueAsString(offer));
			offerCollection.insert(jsonDoc);
			offer.setId((ObjectId) jsonDoc.getObjectId("_id"));
		}
	}

	@Override
	public void reset() {
		offerCollection.drop();
	}

	@Override
	public void close() {
		// TODO do nothing
	}
}
