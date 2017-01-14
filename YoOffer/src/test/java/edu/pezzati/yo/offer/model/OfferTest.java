package edu.pezzati.yo.offer.model;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private Integer amount;
    private Double lat;
    private Double lon;
    private Offer expected;

    @Before
    public void init() {
	offerId = new ObjectId();
	title = "title";
	desc = "desc";
	ownerId = new ObjectId();
	price = 10D;
	amount = 1;
	lat = 10D;
	lon = 10D;
	expected = new Offer(offerId, title, desc, ownerId, price, amount, lat, lon);
    }

    @Test
    public void equalsHashCode() {
	Offer actual = new Offer(offerId, title, desc, ownerId, price, amount, lat, lon);
	Assert.assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
	double anotherLon = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, amount, lat, anotherLon);
	Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    public void equalsOffer() {
	Offer actual = new Offer(offerId, title, desc, ownerId, price, amount, lat, lon);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsNull() {
	Offer actual = null;
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void offersAreEqualsDespiteAmount() {
	Integer differentAmount = 10;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, differentAmount, lat, lon);
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void notEqualsById() {
	ObjectId differentId = new ObjectId();
	Offer actual = new Offer(differentId, title, desc, ownerId, price, amount, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByTitle() {
	String differentTitle = "different title";
	Offer actual = new Offer(offerId, differentTitle, desc, ownerId, price, amount, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByDesc() {
	String differentDesc = "different desc";
	Offer actual = new Offer(offerId, title, differentDesc, ownerId, price, amount, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByOwnerId() {
	ObjectId differentOwnerId = new ObjectId();
	Offer actual = new Offer(offerId, title, desc, differentOwnerId, price, amount, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByPrice() {
	double differentPrice = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, differentPrice, amount, lat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByLat() {
	double differentLat = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, amount, differentLat, lon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void notEqualsByLon() {
	double differentLon = 20D;
	Offer actual = new Offer(offerId, title, desc, ownerId, price, amount, lat, differentLon);
	Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void offerEqualsObject() {
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
    public void offerAmountMustHaveMinMaxAndNotNullAnnotations() throws Exception {
	String fieldName = "amount";
	Field field = Offer.class.getDeclaredField(fieldName);
	Min min = (Min) AnnotationResolver.getFieldAnnotationByType(field, Min.class);
	Assert.assertEquals(1, min.value());

	Max max = (Max) AnnotationResolver.getFieldAnnotationByType(field, Max.class);
	Assert.assertEquals(999999999, max.value());

	NotNull notNull = (NotNull) AnnotationResolver.getFieldAnnotationByType(field, NotNull.class);
	Assert.assertNotNull(notNull);
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

    @Test
    public void cannotPickOffer() {
	Integer pickAmount = 10;
	Offer pick = new Offer(offerId, title, desc, ownerId, price, pickAmount, lat, lon);
	Assert.assertFalse(pick.canPick(expected));
    }

    @Test
    public void canPickOffer() {
	Integer pickAmount = 1;
	Offer pick = new Offer(offerId, title, desc, ownerId, price, pickAmount, lat, lon);
	Assert.assertTrue(pick.canPick(expected));
    }
}
