package edu.pezzati.yo.offer;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pezzati.yo.offer.exception.NotEnoughOfferElements;
import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceIT {

	private static String webContext;
	private static final String PROTOCOL = "http://";
	private static final String PORT = ":8080";
	private static final String YOOFFER = "/YoOffer/srv/offer";
	private static final String PICKURL = "/pick";
	private Offer offer;

	// @PersistenceUnit(name = "yodb")
	// private OfferPersistenceServiceImpl persistenceService;
	// @Inject
	// @InjectMocks
	// private OfferServiceImpl offerService;
	public static final int OK = 200;
	public static final int INVALID_INPUT = 400;
	public static final int NOT_FOUND = 404;
	public static final int ERROR = 500;
	private Client client;
	private WebTarget webTarget;
	private WebTarget pickWebTarget;

	@BeforeClass
	public static void setup() {
		webContext = PROTOCOL + System.getProperty("webContext") + PORT + YOOFFER;
	}

	@Before
	public void init() throws URISyntaxException {
		client = ClientBuilder.newClient();
		webTarget = client.target(new URI(webContext));
		pickWebTarget = client.target(new URI(webContext + PICKURL));
	}

	@Test
	public void createNullOffer() throws URISyntaxException {
		offer = null;
		Response response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void createInvalidOffer() {
		offer = new Offer(null, null, null, null, null, 4, 200D, 90D);
		Response response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
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
		Response response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(OK, response.getStatus());
		Offer actualOffer = response.readEntity(Offer.class);
		offer.setId(actualOffer.getId());
		Assert.assertEquals(offer, actualOffer);
	}

	@Test
	public void createOfferError() {
		offer = new Offer();
		Response response = webTarget.request().post(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(ERROR, response.getStatus());
	}

	@Test
	public void readOfferByNullValue() throws OfferNotFound {
		ObjectId offerId = null;
		Response response = webTarget.queryParam("id", offerId).request().get();
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void readOfferByNonExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		Response response = webTarget.queryParam("id", offerId).request().get();
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void readOfferByExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		Response response = webTarget.queryParam("id", offerId).request().get();
		Assert.assertEquals(OK, response.getStatus());
	}

	@Test
	public void readOfferError() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		Response response = webTarget.queryParam("id", offerId).request().get();
		Assert.assertEquals(ERROR, response.getStatus());
	}

	@Test
	public void updateOfferByNullValue() throws OfferNotFound {
		ObjectId ownerId = new ObjectId();
		offer = new Offer(null, "title", "desc", ownerId, 1D, 5, 2D, 3D);
		Response response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void updateOfferByNonExistingOne() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", ownerId, 1D, 5, 2D, 3D);
		Response response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void updateOfferByExistingButInvalidOne() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", null, 1D, 5, 2D, 3D);
		Response response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void updateOfferByExistingAndValidOne() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", ownerId, 1D, 5, 2D, 3D);
		Response response = webTarget.request().put(Entity.entity(offer, MediaType.APPLICATION_JSON));
		Assert.assertEquals(OK, response.getStatus());
	}

	@Test
	public void deleteOfferByNullValue() throws OfferNotFound {
		ObjectId offerId = null;
		Response response = webTarget.queryParam("id", offerId).request().delete();
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void deleteOfferByNonExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		Response response = webTarget.queryParam("id", offerId).request().delete();
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void deleteOfferByExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		Offer expectedOffer = new Offer(offerId, "title", "desc", ownerId, 1D, 5, 2D, 3D);
		Response response = webTarget.queryParam("id", offerId).request().delete();
		Offer actualOffer = (Offer) response.getEntity();
		Assert.assertEquals(OK, response.getStatus());
		Assert.assertEquals(expectedOffer, actualOffer);
	}

	@Test
	public void pickItemsAboutNonExistingOffer() throws OfferNotFound, NotEnoughOfferElements {
		Response response = webTarget.queryParam("id", new ObjectId()).request().delete();
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void pickTooMuchItems() throws OfferNotFound, NotEnoughOfferElements {
		Offer offerToPick = new Offer();
		Response response = pickWebTarget.request().put(Entity.entity(offerToPick, MediaType.APPLICATION_JSON));
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void pickEnoughItems() throws OfferNotFound, NotEnoughOfferElements {
		Offer offerToPick = new Offer();
		Response response = pickWebTarget.request().put(Entity.entity(offerToPick, MediaType.APPLICATION_JSON));
		Assert.assertEquals(OK, response.getStatus());
	}
}
