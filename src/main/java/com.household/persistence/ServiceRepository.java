package com.household.persistence;

import com.household.entity.Service;
import com.household.entity.enums.ServiceType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by artemvlasov on 05/10/15.
 */
public interface ServiceRepository extends MongoRepository<Service, String> {
    List<Service> findByCityNameAndType(String city, ServiceType type);
}
