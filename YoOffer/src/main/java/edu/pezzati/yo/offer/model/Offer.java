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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((desc == null) ? 0 : desc.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((lat == null) ? 0 : lat.hashCode());
	result = prime * result + ((lon == null) ? 0 : lon.hashCode());
	result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Offer other = (Offer) obj;
	if (desc == null) {
	    if (other.desc != null)
		return false;
	} else if (!desc.equals(other.desc))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (lat == null) {
	    if (other.lat != null)
		return false;
	} else if (!lat.equals(other.lat))
	    return false;
	if (lon == null) {
	    if (other.lon != null)
		return false;
	} else if (!lon.equals(other.lon))
	    return false;
	if (ownerId == null) {
	    if (other.ownerId != null)
		return false;
	} else if (!ownerId.equals(other.ownerId))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	return true;
    }
}
