package com.household.service.impl;

import com.household.entity.Address;
import com.household.entity.Payment;
import com.household.persistence.PaymentRepository;
import com.household.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by artemvlasov on 06/10/15.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void add(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment findLastPayment(Address address, String serviceName) {
        Pageable request = new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "paymentDate"));
        return paymentRepository.findByAddressAndServiceName(address, serviceName, request).getContent().stream().findFirst().get();
    }
}
