package com.household.persistence;

import com.household.entity.Payment;
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
    Page<Payment> findByApartmentIdAndServiceTypeAlias (String apartmentId, String type, Pageable pageable);

    long countByApartmentIdAndPaidFalse (String apartmentId);

    List<Payment> findByApartmentIdAndPaidFalse (String apartmentId, Sort sort);

    List<Payment> findByApartmentId (String apartmentId, Sort sort);

    @Query(value = "{'apartmentId' : ?0, 'paymentDate' : {$gte : ?1, $lte : ?2}}")
    List<Payment> findPaymentsBetweenDates (String apartmentId, LocalDate monthStart, LocalDate monthEnd);

    @Query(value = "{'apartmentId' : ?0, 'service.type.alias' : ?1, 'paymentDate' : {$gte : ?2, $lte : ?3}}")
    List<Payment> findPaymentsByServiceTypeAlias (String apartmentId, String type, LocalDate yearStart, LocalDate yearEnd);

    @Query(value = "{'apartmentId' : ?0, 'paymentDate' : {$gte : ?1, $lte : ?2}}")
    List<Payment> findPayments (String apartmentId, LocalDate yearStart, LocalDate yearEnd);
}
