package com.household.service;

import com.household.entity.Address;
import com.household.entity.Payment;

/**
 * Created by artemvlasov on 06/10/15.
 */
public interface PaymentService {
    void add(Payment payment);
    Payment findLastPayment(Address address, String serviceName);
}
