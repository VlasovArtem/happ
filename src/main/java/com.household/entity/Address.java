package com.household.entity;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by artemvlasov on 02/09/15.
 */
@JsonAutoDetect
public class Address {
    @Id
    private String id;
    @NotSaved
    private City city;
    private String street;
    private String house;
    private int apartment;

    public Address() {
    }

    public Address(City city, String street, String house, int apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }
}
