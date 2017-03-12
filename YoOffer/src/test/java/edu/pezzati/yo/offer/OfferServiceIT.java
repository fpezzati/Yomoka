package edu.pezzati.yo.offer;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceIT {

    private static String webContext;
    private static File offerSetFile;
    private static final String PROTOCOL = "http://";
    private static final String PORT = ":8080";
    private static final String YOOFFER = "/YoOffer/srv/offer";
    private static final String PICKURL = "/pick";
    private Offer offer;

    public static final int OK = 200;
    public static final int INVALID_INPUT = 400;
    public static final int NOT_FOUND = 404;
    public static final int ERROR = 500;
    private Client client;
    private WebTarget webTarget;
    private WebTarget pickWebTarget;
    private WebTarget setupDatabase;
    private WebTarget resetDatabase;
    private Response response;
    private List<Offer> offerToTest;
    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void setup() {
	webContext = PROTOCOL + System.getProperty("webContext") + PORT + YOOFFER;
	offerSetFile = new File(PersistenceServiceTest.class.getClassLoader()
		.getResource(PersistenceServiceTest.OFFERSET_PATH).getFile());
	objectMapper = new ObjectMapper();
    }

    @Before
    public void init() throws Exception {
	client = ClientBuilder.newClient();
	webTarget = client.target(new URI(webContext));
	pickWebTarget = client.target(new URI(webContext + PICKURL));
	offer = new Offer(null, "title", "description", new ObjectId(), 1D, 5, 2D, 3D);
	setupDatabase();
    }

    private void setupDatabase() throws Exception {
	offerToTest = objectMapper.readValue(offerSetFile, new TypeReference<List<Offer>>() {
	});
	setupDatabase = client.target(new URI(webContext));
	for (Offer offer : offerToTest) {
	    Response response = setupDatabase.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
	    if (response.getStatus() != 200)
		throw new Exception("Setup database fails.");
	    offer.setId(response.readEntity(Offer.class).getId());
	    response.close();
	}
    }

    private void resetDatabase() throws Exception {
	for (Offer offer : offerToTest) {
	    if (offer.getId() != null) {
		resetDatabase = client.target(new URI(webContext + "/" + offer.getId().toString()));
		Response response = resetDatabase.request(MediaType.APPLICATION_JSON).delete();
		if (response.getStatus() != 200 && response.getStatus() != 404)
		    throw new Exception("Reset database fails.");
		response.close();
	    }
	}
	offerToTest = objectMapper.readValue(offerSetFile, new TypeReference<List<Offer>>() {
	});
    }

    @After
    public void cleanup() throws Exception {
	if (response != null) {
	    response.close();
	}
	resetDatabase();
    }

    @Test
    public void createNullOffer() throws URISyntaxException {
	offer = null;
	response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void createInvalidOffer() {
	offer = new Offer(null, null, null, null, null, 4, 200D, 90D);
	response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void createValidOffer() throws Exception {
	String title = "title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	double price = 1D;
	int amount = 5;
	double lat = 2D;
	double lon = 3D;
	offer = new Offer(null, title, desc, ownerId, price, amount, lat, lon);
	response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(OK, response.getStatus());
	Offer actualOffer = response.readEntity(Offer.class);
	offer.setId(actualOffer.getId());
	offerToTest.add(offer);
	Assert.assertEquals(offer, actualOffer);
    }

    @Test
    public void readOfferByNonExistingId() throws OfferNotFound, URISyntaxException {
	ObjectId offerId = new ObjectId();
	webTarget = client.target(new URI(webContext + "/" + offerId.toString()));
	response = webTarget.request(MediaType.APPLICATION_JSON).get();
	Assert.assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void readOfferByExistingId() throws OfferNotFound, URISyntaxException {
	ObjectId offerId = offerToTest.get(0).getId();
	webTarget = client.target(new URI(webContext + "/" + offerId.toString()));
	response = webTarget.request(MediaType.APPLICATION_JSON).get();
	Assert.assertEquals(OK, response.getStatus());
    }

    @Test
    public void updateOfferByNullValue() throws OfferNotFound {
	ObjectId ownerId = new ObjectId();
	offer = new Offer(null, "title", "desc", ownerId, 1D, 5, 2D, 3D);
	response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void updateOfferByNonExistingOne() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	ObjectId ownerId = new ObjectId();
	offer = new Offer(offerId, "title", "desc", ownerId, 1D, 5, 2D, 3D);
	response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void updateOfferByExistingButInvalidOne() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	offer = new Offer(offerId, "title", "desc", null, 1D, 5, 200D, 3D);
	response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void updateOfferByExistingAndValidOne() throws OfferNotFound {
	ObjectId offerId = offerToTest.get(0).getId();
	ObjectId ownerId = new ObjectId();
	offer = new Offer(offerId, "title", "desc", ownerId, 1D, 5, 2.5D, 3.6D);
	response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(OK, response.getStatus());
    }

    @Test
    public void deleteOfferByNonExistingId() throws OfferNotFound, URISyntaxException {
	ObjectId offerId = new ObjectId();
	webTarget = client.target(new URI(webContext + "/" + offerId.toString()));
	response = webTarget.request(MediaType.APPLICATION_JSON).delete();
	Assert.assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void deleteOfferByExistingId() throws OfferNotFound, URISyntaxException {
	ObjectId offerId = offerToTest.get(0).getId();
	webTarget = client.target(new URI(webContext + "/" + offerId.toString()));
	response = webTarget.request(MediaType.APPLICATION_JSON).delete();
	Assert.assertEquals(OK, response.getStatus());
    }

    @Test
    public void pickItemsAboutNonExistingOffer() throws OfferNotFound, NotEnoughOfferElements {
	ObjectId offerId = new ObjectId();
	offer = new Offer(offerId, "title", "desc", null, 1D, 5, 200D, 3D);
	response = pickWebTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
	Assert.assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void pickTooMuchItems() throws OfferNotFound, NotEnoughOfferElements {
	Offer offerToPick = new Offer();
	response = pickWebTarget.request().put(Entity.entity(offerToPick, MediaType.APPLICATION_JSON));
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void pickEnoughItems() throws OfferNotFound, NotEnoughOfferElements {
	ObjectId offerId = offerToTest.get(0).getId();
	Offer offerToPick = new Offer(offerId, null, null, null, null, 3, null, null);
	response = pickWebTarget.request().put(Entity.entity(offerToPick, MediaType.APPLICATION_JSON));
	Assert.assertEquals(OK, response.getStatus());
    }
}
