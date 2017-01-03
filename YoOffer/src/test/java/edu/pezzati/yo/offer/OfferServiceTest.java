package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createNullOffer() {
	Offer offer = null;
	PersistenceService persistenceService = Mockito.mock(PersistenceService.class);
	OfferService offerService = new OfferServiceImpl(persistenceService);
	Response response = offerService.create(offer);
	Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void createInvalidOffer() {
	Offer offer = new Offer(null, null, null, null, null, 200D, 90D);
	PersistenceService persistenceService = Mockito.mock(PersistenceService.class);
	String errorMessage = "Offer is not valid: " + offer.toString();
	Mockito.when(persistenceService.create(offer)).thenThrow(new IllegalArgumentException(errorMessage));
	OfferService offerService = new OfferServiceImpl(persistenceService);
	Response response = offerService.create(offer);
	Mockito.verify(persistenceService, Mockito.times(1)).create(offer);
	Assert.assertEquals(400, response.getStatus());
	Assert.assertEquals(errorMessage, response.getEntity());
    }

    public void createPartiallyValuedValidOffer() {

    }

    public void createFullyValuedValidOffer() {

    }

    public void readOfferByNullValue() {

    }

    public void readOfferByNonExistingId() {

    }

    public void readOfferByExistingId() {

    }

    public void updateOfferByNullValue() {

    }

    public void updateOfferByNonExistingOne() {

    }

    public void updateOfferByExistingButInvalidOne() {

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
