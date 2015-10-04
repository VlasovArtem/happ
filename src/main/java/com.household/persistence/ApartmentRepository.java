package com.household.persistence;

import com.household.entity.Apartment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String> {
}
