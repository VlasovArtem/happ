package com.household;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.household.entity.Address;
import com.household.entity.Apartment;
import com.household.entity.City;

/**
 * Created by artemvlasov on 03/09/15.
 */
public class App {
    public static void main(String[] args) throws JsonProcessingException {
//        MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
//        mo.dropCollection("cities");
//        City city = new City();
//        city.setName("Kiev");
//        mo.save(city);
//        MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
//        mo.dropCollection("household");
//        Address address = new Address();
//        address.setCity(City.ODESSA);
//        address.setStreet("Akademika Viliamsa");
//        address.setHouse("59g/2");
//        address.setApartment(77);
//        Apartment apartment = new Apartment();
//        apartment.setAddress(address);
//        mo.save(apartment);
        ObjectMapper mapper = new ObjectMapper();
        Apartment apartment = new Apartment();
        Address address = new Address();
        address.setApartment(77);
        address.setCity(new City("Odessa"));
        address.setHouse("59g/2");
        address.setStreet("Akademika Viliamsa");
        apartment.setAddress(address);
        System.out.println(mapper.writeValueAsString(apartment));
    }
}
