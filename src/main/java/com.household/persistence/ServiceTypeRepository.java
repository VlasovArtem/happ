package com.household.persistence;

import com.household.entity.ServiceType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 13/10/15.
 */
public interface ServiceTypeRepository extends MongoRepository<ServiceType, ObjectId> {
}
