package com.household.persistence;

import com.household.entity.Address;
import com.household.entity.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Created by artemvlasov on 04/10/15.
 */
public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {
    Page<Payment> findByAddressAndServiceName(Address address, String serviceName, Pageable pageable);
}
