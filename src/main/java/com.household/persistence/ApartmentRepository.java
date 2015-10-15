package com.household.persistence;

import com.household.entity.Address;
import com.household.entity.Apartment;
import com.household.entity.City;
import com.household.entity.Street;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String> {

    long countByAddressCityAndAddressStreetAndAddressHouseAndAddressApartment(City city, Street street, String house, int apartment);
}
