package com.household.persistence.custom;

import com.household.entity.Payment;

import java.util.List;

/**
 * Created by artemvlasov on 28/10/15.
 */
public interface PaymentRepositoryCustom {
    List<Payment> findLastOtherPayments(String apartmentId);
}
