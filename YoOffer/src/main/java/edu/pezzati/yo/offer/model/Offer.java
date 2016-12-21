package edu.pezzati.yo.offer.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "desc", "ownerId", "price", "lat", "lon" })
public class Offer {

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
}
