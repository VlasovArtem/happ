package com.household.persistence;

import com.household.entity.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 04/10/15.
 */
public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {
}
