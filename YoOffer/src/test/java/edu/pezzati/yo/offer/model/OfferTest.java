package edu.pezzati.yo.offer.model;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.pezzati.yo.offer.util.AnnotationResolver;

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
    public void notEqualsById() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	ObjectId differentId = new ObjectId();
	Offer actual = new Offer(differentId, title, desc, ownerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByTitle() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	String differentTitle = "different title";
	Offer actual = new Offer(offerId, differentTitle, desc, ownerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByDesc() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	String differentDesc = "different desc";
	Offer actual = new Offer(offerId, title, differentDesc, ownerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByOwnerId() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	ObjectId differentOwnerId = new ObjectId();
	Offer actual = new Offer(offerId, title, desc, differentOwnerId, price, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByPrice() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	double differentPrice = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, differentPrice, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByLat() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	double differentLat = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, differentLat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByLon() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	double differentLon = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, lat, differentLon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void offerEqualsObject() {
	Offer expected = new Offer(offerId, title, desc, ownerId, price, lat, lon);
	Object actual = new Object();
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void offerMustHaveEntityAnnotation() {
	Assert.assertTrue(AnnotationResolver.givenTypeIsAnnotated(Offer.class, Entity.class));
    }

    @Test
    public void offerIdMustHaveIdAndGeneratedValueAnnotations() throws Exception {
	String fieldName = "id";
	Field field = Offer.class.getDeclaredField(fieldName);
	Assert.assertTrue(AnnotationResolver.givenFieldIsAnnotated(field, Id.class, GeneratedValue.class));
    }

    @Test
    public void offerTitleMustHavePatternAndSizeAnnotations() throws Exception {
	String fieldName = "title";
	Field field = Offer.class.getDeclaredField(fieldName);
	Pattern pattern = (Pattern) AnnotationResolver.getFieldAnnotationByType(field, Pattern.class);
	Assert.assertEquals("[\\w\\s]*", pattern.regexp());

	Size size = (Size) AnnotationResolver.getFieldAnnotationByType(field, Size.class);
	Assert.assertEquals(50, size.max());
	Assert.assertEquals(0, size.min());
    }

    @Test
    public void offerDescriptionMustHavePatternAndSizeAnnotations() throws Exception {
	String fieldName = "desc";
	Field field = Offer.class.getDeclaredField(fieldName);
	Pattern pattern = (Pattern) AnnotationResolver.getFieldAnnotationByType(field, Pattern.class);
	Assert.assertEquals("[\\w\\s]*", pattern.regexp());

	Size size = (Size) AnnotationResolver.getFieldAnnotationByType(field, Size.class);
	Assert.assertEquals(200, size.max());
	Assert.assertEquals(0, size.min());
    }

    @Test
    public void offerPriceMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
	String fieldName = "price";
	Field field = Offer.class.getDeclaredField(fieldName);
	Digits digits = (Digits) AnnotationResolver.getFieldAnnotationByType(field, Digits.class);
	Assert.assertEquals(6, digits.integer());
	Assert.assertEquals(2, digits.fraction());

	DecimalMax decimalMax = (DecimalMax) AnnotationResolver.getFieldAnnotationByType(field, DecimalMax.class);
	Assert.assertEquals("999999.99", decimalMax.value());

	DecimalMin decimalMin = (DecimalMin) AnnotationResolver.getFieldAnnotationByType(field, DecimalMin.class);
	Assert.assertEquals("0", decimalMin.value());
    }

    @Test
    public void offerLatMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
	String fieldName = "lat";
	Field field = Offer.class.getDeclaredField(fieldName);
	Digits digits = (Digits) AnnotationResolver.getFieldAnnotationByType(field, Digits.class);
	Assert.assertEquals(3, digits.integer());
	Assert.assertEquals(8, digits.fraction());

	DecimalMax decimalMax = (DecimalMax) AnnotationResolver.getFieldAnnotationByType(field, DecimalMax.class);
	Assert.assertEquals("180.00", decimalMax.value());

	DecimalMin decimalMin = (DecimalMin) AnnotationResolver.getFieldAnnotationByType(field, DecimalMin.class);
	Assert.assertEquals("-180.00", decimalMin.value());
    }

    @Test
    public void offerLonMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
	String fieldName = "lon";
	Field field = Offer.class.getDeclaredField(fieldName);
	Digits digits = (Digits) AnnotationResolver.getFieldAnnotationByType(field, Digits.class);
	Assert.assertEquals(3, digits.integer());
	Assert.assertEquals(8, digits.fraction());

	DecimalMax decimalMax = (DecimalMax) AnnotationResolver.getFieldAnnotationByType(field, DecimalMax.class);
	Assert.assertEquals("90.00", decimalMax.value());

	DecimalMin decimalMin = (DecimalMin) AnnotationResolver.getFieldAnnotationByType(field, DecimalMin.class);
	Assert.assertEquals("-90.00", decimalMin.value());
    }
}
