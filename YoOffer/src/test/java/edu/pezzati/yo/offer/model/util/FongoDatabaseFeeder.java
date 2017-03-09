package edu.pezzati.yo.offer.model.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import edu.pezzati.yo.offer.model.Offer;

public class FongoDatabaseFeeder implements DatabaseFeeder {

	private Fongo fongo;
	private DB db;
	private DBCollection offerCollection;
	private ObjectMapper objectMapper;

	public void init() {
		fongo = new Fongo("yodbtest");
		db = fongo.getDB("yodb");
		offerCollection = db.getCollection("offer");
	}

	@Override
	public void init(String dbUri) {
		init();
	}

	@Override
	public void saveOffer(Offer... offers) throws JsonProcessingException {
		for (Offer offer : offers) {
			offerCollection.insert((DBObject[]) JSON.parse(objectMapper.writeValueAsString(offer)));
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
