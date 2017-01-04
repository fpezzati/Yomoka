package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Offer offer;
	private PersistenceService persistenceService;
	private OfferService offerService;
	private String persistenceUnitName = "yotest";
	private static final int OK = 200;
	private static final int INVALID_INPUT = 400;
	private static final int NOT_FOUND = 404;
	private static final int ERROR = 500;

	@Before
	public void init() {
		persistenceService = Mockito.mock(PersistenceService.class);
		offerService = new OfferServiceImpl(persistenceService);
	}

	@Test
	public void createNullOffer() {
		offer = null;
		Mockito.when(persistenceService.create(offer)).thenThrow(new IllegalArgumentException());
		Response response = offerService.create(offer);
		Mockito.verify(persistenceService, Mockito.times(1)).create(offer);
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void createInvalidOffer() {
		offer = new Offer(null, null, null, null, null, 200D, 90D);
		String expectedMessage = Offer.class.getName() + " is not valid: " + offer.toString();
		Mockito.when(persistenceService.create(offer)).thenThrow(new IllegalArgumentException(expectedMessage));
		Response response = offerService.create(offer);
		Mockito.verify(persistenceService, Mockito.times(1)).create(offer);
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
		String actualMessage = response.getEntity().toString();
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void createValidOffer() throws Exception {
		offer = new Offer(null, "title", "desc", new ObjectId(), 1D, 2D, 3D);
		offerService = new OfferServiceImpl(PersistenceServiceImpl.getInstance(persistenceUnitName));
		Response response = offerService.create(offer);
		Assert.assertEquals(OK, response.getStatus());
		Offer actualOffer = (Offer) response.getEntity();
		offer.setId(actualOffer.getId());
		Assert.assertEquals(offer, actualOffer);
	}

	@Test
	public void createOfferError() {
		offer = new Offer();
		String expectedMessage = "Unexpected internal error";
		Mockito.when(persistenceService.create(offer)).thenThrow(new RuntimeException(expectedMessage));
		Response response = offerService.create(offer);
		Assert.assertEquals(ERROR, response.getStatus());
		String actualMessage = response.getEntity().toString();
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void readOfferByNullValue() throws OfferNotFound {
		ObjectId offerId = null;
		Mockito.when(persistenceService.read(offerId)).thenThrow(new IllegalArgumentException());
		Response response = offerService.read(offerId);
		Mockito.verify(persistenceService, Mockito.times(1)).read(offerId);
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	@Test
	public void readOfferByNonExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		Mockito.when(persistenceService.read(offerId)).thenThrow(new OfferNotFound());
		Response response = offerService.read(offerId);
		Mockito.verify(persistenceService, Mockito.times(1)).read(offerId);
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void readOfferByExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", ownerId, 1D, 2D, 3D);
		Mockito.when(persistenceService.read(offerId)).thenReturn(offer);
		Response response = offerService.read(offerId);
		Mockito.verify(persistenceService, Mockito.times(1)).read(offerId);
		Assert.assertEquals(OK, response.getStatus());
	}

	@Test
	public void readOfferError() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		String expectedMessage = "Unexpected internal error";
		Mockito.when(persistenceService.read(offerId)).thenThrow(new RuntimeException(expectedMessage));
		Response response = offerService.read(offerId);
		Assert.assertEquals(ERROR, response.getStatus());
		String actualMessage = response.getEntity().toString();
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void updateOfferByNullValue() throws OfferNotFound {
		ObjectId ownerId = new ObjectId();
		offer = new Offer(null, "title", "desc", ownerId, 1D, 2D, 3D);
		String expectedMessage = "Invalid offer: " + offer.toString();
		Mockito.when(persistenceService.update(offer)).thenThrow(new IllegalArgumentException(expectedMessage));
		Response response = offerService.update(offer);
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
		String actualMessage = response.getEntity().toString();
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void updateOfferByNonExistingOne() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", ownerId, 1D, 2D, 3D);
		Mockito.when(persistenceService.update(offer)).thenThrow(new OfferNotFound());
		Response response = offerService.update(offer);
		Assert.assertEquals(NOT_FOUND, response.getStatus());
	}

	@Test
	public void updateOfferByExistingButInvalidOne() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		offer = new Offer(offerId, "title", "desc", null, 1D, 2D, 3D);
		Mockito.when(persistenceService.update(offer)).thenThrow(new IllegalArgumentException());
		Response response = offerService.update(offer);
		Assert.assertEquals(INVALID_INPUT, response.getStatus());
	}

	public void updateOfferByExistingAndValidOne() {

	}

	public void deleteOfferByNullValue() {

	}

	public void deleteOfferByNonExistingId() {

	}

	public void deleteOfferByExistingId() {

	}
}
