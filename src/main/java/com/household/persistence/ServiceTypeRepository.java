package com.household.persistence;

import com.household.entity.ServiceType;
import com.household.entity.Subtype;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by artemvlasov on 13/10/15.
 */
public interface ServiceTypeRepository extends MongoRepository<ServiceType, ObjectId> {
    @Query(value = "{group : 'OTHER'}", fields = "{subtypes : 1}")
    ServiceType findSubtypes();
}
