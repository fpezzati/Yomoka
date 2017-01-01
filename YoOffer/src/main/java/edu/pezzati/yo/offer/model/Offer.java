package edu.pezzati.yo.offer.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "desc", "ownerId", "price", "lat", "lon" })
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @JsonSerialize(using = ObjectIdSerializer.class)
    ObjectId id;

    @JsonProperty
    String title;

    @JsonProperty
    String desc;

    @JsonProperty
    @JsonSerialize(using = ObjectIdSerializer.class)
    ObjectId ownerId;

    @JsonProperty
    Double price;

    @JsonProperty
    Double lat;

    @JsonProperty
    Double lon;

    public Offer() {
	// Empty constructor to be a bean
    }

    public Offer(ObjectId id, String title, String desc, ObjectId ownerId, Double price, Double lat, Double lon) {
	this.id = id;
	this.title = title;
	this.desc = desc;
	this.ownerId = ownerId;
	this.price = price;
	this.lat = lat;
	this.lon = lon;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, title, desc, ownerId, price, lat, lon);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Offer offer = (Offer) obj;
	boolean result = Objects.equals(this.id, offer.id);
	result = result && Objects.equals(this.title, offer.title);
	result = result && Objects.equals(this.desc, offer.desc);
	result = result && Objects.equals(this.ownerId, offer.ownerId);
	result = result && Objects.equals(this.price, offer.price);
	result = result && Objects.equals(this.lat, offer.lat);
	result = result && Objects.equals(this.lon, offer.lon);
	return result;
    }
}
