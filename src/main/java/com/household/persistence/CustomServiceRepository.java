package com.household.persistence;

import com.household.entity.CustomService;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 04/11/15.
 */
public interface CustomServiceRepository extends MongoRepository<CustomService, String> {
}
