package edu.pezzati.yo.offer;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.pezzati.yo.offer.model.Offer;

public class OfferServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void createNullOffer() {
		Offer offer = null;
		OfferService offerService = new OfferServiceImpl();
		Response response = offerService.create(offer);
		Assert.assertEquals(400, response.getStatus());
	}

	public void createInvalidOffer() {

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
