package com.household.persistence;

import com.household.entity.Apartment;
import com.household.entity.City;
import com.household.entity.Street;
import com.household.persistence.custom.ApartmentRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String>, ApartmentRepositoryCustom {
    List<Apartment> findByOwnerId(String ownerId);
    long countByOwnerId (String ownerId);
    long countByAddressCityAndAddressStreetAndAddressHouseAndAddressApartment(City city, Street street, String house, int apartment);
    long countByOwnerIdAndId (String ownerId, String apartmentId);
    @Query(value = "{id : ?0}", fields = "{'address.city' : 1}")
    Apartment findApartmentCity (String apartmentId);
}
