package edu.pezzati.yo.offer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.pezzati.yo.offer.model.Offer;

public class PersistenceServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static String persistenceUnitName;
	private static OfferPersistenceService persistenceService;
	private Offer offer;

	@BeforeClass
	public static void init() {
		persistenceUnitName = "yotest";
		persistenceService = new OfferPersistenceServiceImpl(persistenceUnitName);
	}

	@AfterClass
	public static void destroy() {
		persistenceService.dispose();
	}

	@Test
	public void createNullOffer() {
		offer = null;
		expectedException.expect(IllegalArgumentException.class);
		persistenceService.create(offer);
	}

	@Test
	public void createInvalidOffer() {
		Offer offer = new Offer();
		expectedException.expect(IllegalArgumentException.class);
		persistenceService.create(offer);
	}

	public void createOffer() {

	}

	public void readOffer() {

	}

	public void updateOffer() {

	}

	public void deleteOffer() {

	}
}
