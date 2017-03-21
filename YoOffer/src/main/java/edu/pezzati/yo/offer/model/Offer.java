package edu.pezzati.yo.offer.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;

@ApiModel
@Entity
@Table(name = "offer")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "desc", "ownerId", "price", "amount", "lat", "lon" })
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @JsonProperty
    @Pattern(regexp = "[\\w\\s]*")
    @Size(min = 0, max = 50)
    private String title;

    @JsonProperty
    @Pattern(regexp = "[\\w\\s]*")
    @Size(min = 0, max = 200)
    private String desc;

    @JsonProperty
    @JsonSerialize(using = ObjectIdSerializer.class)
    @NotNull
    private ObjectId ownerId;

    @JsonProperty
    @Digits(integer = 6, fraction = 2)
    @DecimalMax("999999.99")
    @DecimalMin("0")
    private Double price;

    @JsonProperty
    @NotNull
    @Min(1)
    @Max(999999999)
    private Integer amount;

    @JsonProperty
    @Digits(integer = 3, fraction = 8)
    @DecimalMax("180.00")
    @DecimalMin("-180.00")
    private Double lat;

    @JsonProperty
    @Digits(integer = 3, fraction = 8)
    @DecimalMax("90.00")
    @DecimalMin("-90.00")
    private Double lon;

    public Offer() {
	// Entity empty constructor.
    }

    public Offer(ObjectId id, String title, String desc, ObjectId ownerId, Double price, Integer amount, Double lat,
	    Double lon) {
	this.id = id;
	this.title = title;
	this.desc = desc;
	/**
	 * Here is a SONAR false positive about missing initialization of this
	 * not null field. It is a bug as reported <a href=
	 * "https://jira.sonarsource.com/browse/SONARJAVA-1681">here</a>.
	 */
	this.ownerId = ownerId;
	this.price = price;
	this.amount = amount;
	this.lat = lat;
	this.lon = lon;
    }

    public ObjectId getId() {
	return id;
    }

    public void setId(ObjectId id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public ObjectId getOwnerId() {
	return ownerId;
    }

    public void setOwnerId(ObjectId ownerId) {
	this.ownerId = ownerId;
    }

    public Double getPrice() {
	return price;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public Integer getAmount() {
	return amount;
    }

    public void setAmount(Integer amount) {
	this.amount = amount;
    }

    public Double getLat() {
	return lat;
    }

    public void setLat(Double lat) {
	this.lat = lat;
    }

    public Double getLon() {
	return lon;
    }

    public void setLon(Double lon) {
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
	if (!Objects.equals(this.id, offer.id))
	    return false;
	if (!Objects.equals(this.title, offer.title))
	    return false;
	if (!Objects.equals(this.desc, offer.desc))
	    return false;
	if (!Objects.equals(this.ownerId, offer.ownerId))
	    return false;
	if (!Objects.equals(this.price, offer.price))
	    return false;
	if (!Objects.equals(this.lat, offer.lat))
	    return false;
	if (!Objects.equals(this.lon, offer.lon))
	    return false;
	return true;
    }

    public boolean canPick(Offer expected) {
	return this.amount.compareTo(expected.amount) < 1;
    }
}
