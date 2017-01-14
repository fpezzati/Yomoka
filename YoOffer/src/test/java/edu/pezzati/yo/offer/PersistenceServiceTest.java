package edu.pezzati.yo.offer;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.WeldRunner;

@RunWith(WeldRunner.class)
public class PersistenceServiceTest {

    private static final String OFFERSET_PATH = "PERSISTENCE/offerset.js";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    @PersistenceUnit(name = "yotest")
    private OfferPersistenceService persistenceService;
    private Offer offer;
    private List<Offer> offerToTest;

    private ObjectMapper objectMapper;

    private static File offerSetFile;

    @BeforeClass
    public static void loadTestData() {
	offerSetFile = new File(PersistenceServiceTest.class.getClassLoader().getResource(OFFERSET_PATH).getFile());
    }

    @Before
    public void init() throws Exception {
	offer = new Offer(null, "title", "description", new ObjectId(), 1D, 2D, 3D);
	objectMapper = new ObjectMapper();
	offerToTest = objectMapper.readValue(offerSetFile, new TypeReference<List<Offer>>() {
	});
	for (Offer offerTest : offerToTest) {
	    Offer createdOffer = persistenceService.create(offerTest);
	    offerTest.setId(createdOffer.getId());
	}
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
    public void createValidButAlreadyIdProvidedOffer() {
	offer.setId(new ObjectId());
	expectedException.expect(Exception.class);
	persistenceService.create(offer);
    }

    @Test
    public void createValidOffer() {
	Offer actualOffer = persistenceService.create(offer);
	Assert.assertTrue(actualOffer.getId() != null);
    }

    @Test
    public void readOfferByNullValue() throws OfferNotFound {
	ObjectId offerId = null;
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.read(offerId);

    }

    @Test
    public void readOfferByNonExistingId() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	expectedException.expect(OfferNotFound.class);
	persistenceService.read(offerId);
    }

    @Test
    public void readOfferByExistingId() throws OfferNotFound {
	Offer expectedOffer = offerToTest.get(0);
	Offer actualOffer = persistenceService.read(expectedOffer.getId());
	Assert.assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void updateOfferByNullValue() throws OfferNotFound {
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.update(offer);
    }

    @Test
    public void updateOfferByNonExistingOne() throws OfferNotFound {
	offer.setId(new ObjectId());
	expectedException.expect(OfferNotFound.class);
	persistenceService.update(offer);
    }

    @Test
    public void updateOfferByExistingButInvalidOne() throws OfferNotFound {
	Offer offerToUpdate = offerToTest.get(0);
	offerToUpdate.setLat(500D);
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.update(offerToUpdate);
    }

    @Test
    public void updateOfferByExistingAndValidOne() throws OfferNotFound {
	Offer expectedOffer = offerToTest.get(0);
	String title = "a brand new title";
	expectedOffer.setTitle(title);
	Offer actualOffer = persistenceService.update(expectedOffer);
	Assert.assertEquals(expectedOffer.getTitle(), actualOffer.getTitle());
    }

    @Test
    public void deleteOfferByNullValue() throws OfferNotFound {
	ObjectId offerId = null;
	expectedException.expect(IllegalArgumentException.class);
	persistenceService.delete(offerId);
    }

    @Test
    public void deleteOfferByNonExistingId() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	expectedException.expect(OfferNotFound.class);
	persistenceService.delete(offerId);
    }

    @Test
    public void deleteOfferByExistingId() throws OfferNotFound {
	ObjectId offerId = offerToTest.get(0).getId();
	persistenceService.delete(offerId);
	expectedException.expect(OfferNotFound.class);
	persistenceService.read(offerId);
    }
}
