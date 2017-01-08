package edu.pezzati.yo.offer;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.pezzati.yo.offer.exception.OfferNotFound;
import edu.pezzati.yo.offer.model.Offer;
import edu.pezzati.yo.offer.util.AnnotationResolver;
import edu.pezzati.yo.offer.util.WeldRunner;

@RunWith(WeldRunner.class)
public class OfferServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Offer offer;
    @Mock
    private OfferPersistenceServiceImpl persistenceService;
    @Inject
    @InjectMocks
    private OfferServiceImpl offerService;
    private static final int OK = 200;
    private static final int INVALID_INPUT = 400;
    private static final int NOT_FOUND = 404;
    private static final int ERROR = 500;

    @Before
    public void init() {
	MockitoAnnotations.initMocks(this);
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
	String title = "title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	double price = 1D;
	double lat = 2D;
	double lon = 3D;
	offer = new Offer(null, title, desc, ownerId, price, lat, lon);
	Offer offerToReturn = new Offer(new ObjectId(), title, desc, ownerId, price, lat, lon);
	Mockito.when(persistenceService.create(offer)).thenReturn(offerToReturn);
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

    @Test
    public void updateOfferByExistingAndValidOne() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	ObjectId ownerId = new ObjectId();
	offer = new Offer(offerId, "title", "desc", ownerId, 1D, 2D, 3D);
	Mockito.when(persistenceService.update(offer)).thenReturn(offer);
	Response response = offerService.update(offer);
	Assert.assertEquals(OK, response.getStatus());
    }

    @Test
    public void deleteOfferByNullValue() throws OfferNotFound {
	ObjectId offerId = null;
	Mockito.when(persistenceService.delete(offerId)).thenThrow(new IllegalArgumentException());
	Response response = offerService.delete(offerId);
	Assert.assertEquals(INVALID_INPUT, response.getStatus());
    }

    @Test
    public void deleteOfferByNonExistingId() throws OfferNotFound {
	ObjectId offerId = new ObjectId();
	Mockito.when(persistenceService.delete(offerId)).thenThrow(new OfferNotFound());
	Response response = offerService.delete(offerId);
	Assert.assertEquals(NOT_FOUND, response.getStatus());
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

    @Test
    public void isOfferServicePathCorrectlyAnnotated() {
	Path path = (Path) AnnotationResolver.getTypeAnnotationByAnnotationType(OfferService.class, Path.class);
	String expectedPath = "/offer";
	String actualPath = path.value();
	Assert.assertEquals(expectedPath, actualPath);
    }

    @Test
    public void isOfferServiceCreateMethodCorrectlyAnnotated() throws Exception {
	Method method = OfferService.class.getMethod("create", Offer.class);
	POST post = (POST) AnnotationResolver.getMethodAnnotationByAnnotationType(method, POST.class);
	Assert.assertNotNull(post);
    }

    @Test
    public void isOfferServiceReadMethodCorrectlyAnnotated() throws Exception {
	Method method = OfferService.class.getMethod("read", ObjectId.class);
	GET get = (GET) AnnotationResolver.getMethodAnnotationByAnnotationType(method, GET.class);
	Assert.assertNotNull(get);
	Path path = (Path) AnnotationResolver.getMethodAnnotationByAnnotationType(method, Path.class);
	String expectedPath = "/{id}";
	String actualPath = path.value();
	Assert.assertEquals(expectedPath, actualPath);
    }

    @Test
    public void isOfferServiceUpdateMethodCorrectlyAnnotated() throws Exception {
	Method method = OfferService.class.getMethod("update", Offer.class);
	PUT put = (PUT) AnnotationResolver.getMethodAnnotationByAnnotationType(method, PUT.class);
	Assert.assertNotNull(put);
    }

    @Test
    public void isOfferServiceDeleteMethodCorrectlyAnnotated() throws Exception {
	Method method = OfferService.class.getMethod("delete", ObjectId.class);
	DELETE delete = (DELETE) AnnotationResolver.getMethodAnnotationByAnnotationType(method, DELETE.class);
	Assert.assertNotNull(delete);
	Path path = (Path) AnnotationResolver.getMethodAnnotationByAnnotationType(method, Path.class);
	String expectedPath = "/{id}";
	String actualPath = path.value();
	Assert.assertEquals(expectedPath, actualPath);
    }
}
