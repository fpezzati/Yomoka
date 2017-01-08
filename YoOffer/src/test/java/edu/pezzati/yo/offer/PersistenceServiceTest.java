package edu.pezzati.yo.offer;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.WeldRunner;

@RunWith(WeldRunner.class)
public class PersistenceServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    @PersistenceUnit(name = "yotest")
    private OfferPersistenceService persistenceService;
    private Offer offer;

    @Before
    public void init() {
	offer = new Offer(null, "title", "description", new ObjectId(), 1D, 2D, 3D);
    }

    @Test
    public void createNullOffer() {
	offer = null;
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createOfferWithInvalidTitle() {
	String title = "this title has invalid char $title!@";
	offer.setTitle(title);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createOfferWithInvalidDesc() {
	String description = "this description is too long! this description is too long! this description is too long! this description is too long! this description is too long! this description is too long! this description is too long!";
	offer.setDesc(description);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createOfferWithInvalidPrice() {
	double price = 1000000D;
	offer.setPrice(price);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createOfferWithInvalidLat() {
	double lat = -200D;
	offer.setLat(lat);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createOfferWithInvalidLon() {
	double lon = 100D;
	offer.setLon(lon);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.create(offer);
    }

    @Test
    public void createValidOffer() {
	Offer actualOffer = persistenceService.create(offer);
	Assert.assertTrue(actualOffer.getId() != null);
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
