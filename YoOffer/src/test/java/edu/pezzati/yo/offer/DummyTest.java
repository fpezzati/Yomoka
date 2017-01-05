package edu.pezzati.yo.offer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.WeldRunner;

@RunWith(WeldRunner.class)
public class DummyTest {

	@Inject
	@PersistenceUnit(name = "yotest")
	private OfferPersistenceService persistenceService;
	private OfferService offerService;
	private static final int OK = 200;

	@Before
	public void init() {
		persistenceService = Mockito.mock(OfferPersistenceService.class);
		offerService = new OfferServiceImpl(persistenceService);
	}

	@Test
	public void deleteOfferByExistingId() throws OfferNotFound {
		ObjectId offerId = new ObjectId();
		ObjectId ownerId = new ObjectId();
		Offer expectedOffer = new Offer(offerId, "title", "desc", ownerId, 1D, 2D, 3D);
		Mockito.when(persistenceService.delete(offerId)).thenReturn(expectedOffer);
		Response response = offerService.delete(offerId);
		Offer actualOffer = (Offer) response.getEntity();
		Assert.assertEquals(OK, response.getStatus());
		Assert.assertEquals(expectedOffer, actualOffer);
	}
}
