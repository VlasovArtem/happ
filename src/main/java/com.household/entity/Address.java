package com.household.entity;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by artemvlasov on 02/09/15.
 */
@JsonAutoDetect
public class Address {
    @Id
    private String id;
    @Embedded
    private City city;
    @Embedded
    private Street street;
    private String house;
    private int apartment;

    public Address() {
    }

    public Address(City city, Street street, String house, int apartment) {
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

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
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
