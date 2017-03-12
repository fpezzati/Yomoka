package edu.pezzati.yo.offer.model.util;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.pezzati.yo.offer.model.Offer;

public class MongoDatabaseFeeder implements DatabaseFeeder {

	private MongoClientURI mongoUri;
	private MongoClient mongoClient;
	private MongoDatabase mongoDb;
	private MongoCollection<Document> offerCollection;
	private ObjectMapper objectMapper;

	@Override
	public void init(String dbUri) {
		mongoUri = new MongoClientURI(dbUri);
		mongoClient = new MongoClient();
		mongoDb = mongoClient.getDatabase(mongoUri.getDatabase());
		offerCollection = mongoDb.getCollection("offer");
		objectMapper = new ObjectMapper();
	}

	@Override
	public void saveOffer(Offer... offers) throws JsonProcessingException {
		for (Offer offer : offers) {
			offerCollection.insertOne(Document.parse(objectMapper.writeValueAsString(offer)));
		}
	}

	@Override
	public void reset() {
		offerCollection.drop();
	}

	@Override
	public void close() {
		mongoClient.close();
	}
}
