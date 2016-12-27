package edu.pezzati.yo.offer;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import edu.pezzati.yo.offer.model.Offer;

public class OfferTest {

    @Test
    public void hashCodeTest() {
	ObjectId offerId = new ObjectId();
	String title = "title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	Double price = 10D;
	Double lat = 10D;
	Double lon = 10D;
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void equalsTest() {
	ObjectId offerId = new ObjectId();
	String title = "title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	Double price = 10D;
	Double lat = 10D;
	Double lon = 10D;
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsNullTest() {
	ObjectId offerId = new ObjectId();
	String title = "title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	Double price = 10D;
	Double lat = 10D;
	Double lon = 10D;
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = null;
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsTest() {
	ObjectId offerId = new ObjectId();
	String title = "title";
	String differentTitle = "another title";
	String desc = "desc";
	ObjectId ownerId = new ObjectId();
	Double price = 10D;
	Double lat = 10D;
	Double lon = 10D;
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Offer actual = new Offer(offerId, differentTitle, desc, ownerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }
}
