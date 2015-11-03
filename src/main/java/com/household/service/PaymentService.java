package com.household.service;

import com.household.entity.Payment;
import com.household.entity.enums.ServiceTypeAlias;

import java.time.Month;
import java.util.List;

/**
 * Created by artemvlasov on 06/10/15.
 */
public interface PaymentService {

    void add(Payment payment);

    Payment findLastPayment (String apartmentId, String type);

    long countUnpaid (String apartmentId);

    List<Payment> findUnpaid (String apartmentId);

    List<Payment> find (String apartmentId);

    void setPaid (String id);

    List<Payment> get(String apartmentId, Month month, int year);

    List<Payment> get(String apartmentId, String type, int year);

    List<Payment> get(String apartmentId, int year);

    List<Payment> getLastWithTypeOther (String apartmentId);
}
