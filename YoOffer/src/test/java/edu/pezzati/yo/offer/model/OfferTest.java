package edu.pezzati.yo.offer.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;

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

	@Test
	public void offerMustHaveEntityAnnotation() {
		Assert.assertTrue(givenTypeIsAnnotated(Offer.class, Entity.class));
	}

	@Test
	public void offerIdMustHaveIdAndGeneratedValueAnnotations() {
		String fieldName = "id";
		Assert.assertTrue(givenTypeHasAnnotatedAttribute(Offer.class, fieldName, Id.class, GeneratedValue.class));
	}

	@Test
	public void offerTitleMustHavePatternAndSizeAnnotations() throws Exception {
		String fieldName = "title";
		Pattern pattern = (Pattern) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Pattern.class);
		Assert.assertEquals("[\\w\\s]*", pattern.regexp());

		Size size = (Size) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Size.class);
		Assert.assertEquals(50, size.max());
		Assert.assertEquals(0, size.min());
	}

	@Test
	public void offerDescriptionMustHavePatternAndSizeAnnotations() throws Exception {
		String fieldName = "desc";
		Pattern pattern = (Pattern) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Pattern.class);
		Assert.assertEquals("[\\w\\s]*", pattern.regexp());

		Size size = (Size) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Size.class);
		Assert.assertEquals(200, size.max());
		Assert.assertEquals(0, size.min());
	}

	@Test
	public void offerPriceMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
		String fieldName = "price";
		Digits digits = (Digits) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Digits.class);
		Assert.assertEquals(6, digits.integer());
		Assert.assertEquals(2, digits.fraction());

		DecimalMax decimalMax = (DecimalMax) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMax.class);
		Assert.assertEquals("999999.99", decimalMax.value());

		DecimalMin decimalMin = (DecimalMin) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMin.class);
		Assert.assertEquals("0", decimalMin.value());
	}

	@Test
	public void offerLatMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
		String fieldName = "lat";
		Digits digits = (Digits) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Digits.class);
		Assert.assertEquals(3, digits.integer());
		Assert.assertEquals(2, digits.fraction());

		DecimalMax decimalMax = (DecimalMax) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMax.class);
		Assert.assertEquals("180.00", decimalMax.value());

		DecimalMin decimalMin = (DecimalMin) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMin.class);
		Assert.assertEquals("-180.00", decimalMin.value());
	}

	@Test
	public void offerLonMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
		String fieldName = "lon";
		Digits digits = (Digits) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName), Digits.class);
		Assert.assertEquals(3, digits.integer());
		Assert.assertEquals(2, digits.fraction());

		DecimalMax decimalMax = (DecimalMax) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMax.class);
		Assert.assertEquals("90.00", decimalMax.value());

		DecimalMin decimalMin = (DecimalMin) getFieldAnnotationByType(Offer.class.getDeclaredField(fieldName),
				DecimalMin.class);
		Assert.assertEquals("-90.00", decimalMin.value());
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
	private boolean givenTypeHasAnnotatedAttribute(Class type, String fieldName, Class... annotations) {
		Field field;
		try {
			field = type.getDeclaredField(fieldName);
		} catch (Exception e) {
			return false;
		}
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

	@SuppressWarnings("rawtypes")
	private Annotation getFieldAnnotationByType(Field field, Class annotationType) {
		for (Annotation typeAnnotation : field.getAnnotations()) {
			if (typeAnnotation.annotationType().equals(annotationType))
				return typeAnnotation;
		}
		throw new NoSuchElementException();
	}
}
