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
    Payment findLastPayment (String addressId, String type);
    long countUnpaid (String addressId);
    List<Payment> findUnpaid (String addressId);
    List<Payment> find (String addressId);
    void setPaid (String id);
    List<Payment> get(String addressId, Month month, int year);
    List<Payment> get(String addressId, String type, int year);
    List<Payment> get(String addressId, int year);
}
