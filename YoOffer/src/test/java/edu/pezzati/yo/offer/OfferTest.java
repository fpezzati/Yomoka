package edu.pezzati.yo.offer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.pezzati.yo.offer.model.Offer;

/**
 * OfferTest checks that Offer entity is configured correctly. Correct
 * configurationi implies correct equals and hashcode implementations. Because
 * Offer is an entity, OfferTest check Offer's JPA annotations.
 * 
 * @author Francesco
 *
 */
public class OfferTest {

    private ObjectId offerId;
    private String title;
    private String desc;
    private ObjectId ownerId;
    private Double price;
    private Double lat;
    private Double lon;

    @Before
    public void init() {
	offerId = new ObjectId();
	title = "title";
	desc = "desc";
	ownerId = new ObjectId();
	price = 10D;
	lat = 10D;
	lon = 10D;
    }

    @Test
    public void equalsHashCode() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	double anotherLon = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, anotherLon);
	Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void equalsOffer() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsNull() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = null;
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEquals() {
	String anotherTitle = "another title";
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, anotherTitle, desc, ownerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void offerEqualsObject() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Object actual = new Object();
	Assert.assertNotEquals(expected, actual);
    }

    /**
     * This test check that Offer is correctly annotated as <a href=
     * "https://docs.oracle.com/javaee/7/api/javax/persistence/Entity.html">Entity</a>.
     */
    @Test
    public void offerIsEntity() {
	Assert.assertTrue(givenTypeIsAnnotated(Offer.class, Entity.class));
    }

    /**
     * This test check that Offer has its id attribute annotated as <a href=
     * "https://docs.oracle.com/javaee/7/api/javax/persistence/Id.html">Id</a>
     * and <a href=
     * "https://docs.oracle.com/javaee/7/api/javax/persistence/GeneratedValue.html">GeneratedValue</a>
     */
    @Test
    public void offerHasId() {
	try {
	    String fieldName = "id";
	    Assert.assertTrue(givenEntityHasAnnotatedAttribute(Offer.class, fieldName, Id.class, GeneratedValue.class));
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail(e.getMessage());
	}

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean givenTypeIsAnnotated(Class type, Class... annotations) {
	for (Class annotation : annotations) {
	    if (!type.isAnnotationPresent(annotation)) {
		return false;
	    }
	}
	return true;
    }

    @SuppressWarnings("rawtypes")
    private boolean givenEntityHasAnnotatedAttribute(Class entity, String fieldName, Class... annotations)
	    throws NoSuchFieldException, SecurityException {
	Field field = entity.getDeclaredField(fieldName);
	Annotation[] fieldAnnotations = field.getAnnotations();
	for (Class annotation : annotations) {
	    int count = 0;
	    for (Annotation fieldAnnotation : fieldAnnotations) {
		if (fieldAnnotation.annotationType().equals(annotation)) {
		    count++;
		}
	    }
	    if (count == 0) {
		return false;
	    }
	}
	return true;
    }
}
