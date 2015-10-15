package com.household.persistence;

import com.household.entity.Payment;
import com.household.entity.enums.ServiceTypeAlias;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;


/**
 * Created by artemvlasov on 04/10/15.
 */
public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {
    Page<Payment> findByAddressIdAndServiceTypeAlias (ObjectId addressId, String type, Pageable pageable);

    long countByAddressIdAndPaidFalse (ObjectId addressId);

    List<Payment> findByAddressIdAndPaidFalse (ObjectId addressId, Sort sort);

    List<Payment> findByAddressId (ObjectId addressId, Sort sort);

    @Query(value = "{'address.id' : {$eq : ?0}, 'paymentDate' : {$gte : ?1, $lte : ?2}}")
    List<Payment> findPaymentsBetweenDates (ObjectId addressId, LocalDate monthStart, LocalDate monthEnd);

    @Query(value = "{'address.id' : ?0, 'service.type' : ?1, 'paymentDate' : {$gte : ?2, $lte : ?3}}")
    List<Payment> findPaymentsByServiceTypeAlias (ObjectId addressId, String type, LocalDate yearStart, LocalDate yearEnd);

    @Query(value = "{'address.id' : ?0, 'paymentDate' : {$gte : ?1, $lte : ?2}}")
    List<Payment> findPayments (ObjectId addressId, LocalDate yearStart, LocalDate yearEnd);
}
