package com.household.persistence;

import com.household.entity.Service;
import com.household.entity.enums.ServiceTypeAlias;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by artemvlasov on 05/10/15.
 */
public interface ServiceRepository extends MongoRepository<Service, ObjectId> {
    List<Service> findByCityAliasAndTypeAlias (String city, String type);
    @Query(value = "{ 'city.alias' : ?0 , 'type.subtypes' : { '$exists' : true} , 'type.subtypes.alias' : ?1}")
    List<Service> findByCityAliasTypeSubtypesAlias (String city, String subtype);
    List<Service> findByCityAlias (String city, Sort sort);
}
