package com.household.persistence;

import com.household.entity.City;
import com.household.entity.Street;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by artemvlasov on 13/10/15.
 */
public interface StreetRepository extends MongoRepository<Street, ObjectId> {
    @Query(value = "{ name : { $regex : ?0, $options : 'i' }, 'city.alias' : ?1 }")
    List<Street> searchStreet(String text, String city);
}
