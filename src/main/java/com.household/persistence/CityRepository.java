package com.household.persistence;

import com.household.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 23/09/15.
 */
public interface CityRepository extends MongoRepository<City, Long> {
}
