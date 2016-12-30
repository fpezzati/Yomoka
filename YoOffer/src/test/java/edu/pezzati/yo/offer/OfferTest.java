package edu.pezzati.yo.offer;

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

    public void offerIsEntity() {

    }

    public void offerHasId() {

    }
}
