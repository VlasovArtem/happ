package com.household.service.impl;

import com.household.entity.Payment;
import com.household.entity.enums.ServiceType;
import com.household.persistence.PaymentRepository;
import com.household.service.PaymentService;
import net.sf.cglib.core.Local;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by artemvlasov on 06/10/15.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void add (Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment findLastPayment (String addressId, ServiceType type) {
        Pageable request = new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "paymentDate"));
        return paymentRepository.findByAddressIdAndServiceType(new ObjectId(addressId), type, request).getContent().stream().findFirst().get();
    }

    @Override
    public long countUnpaid (String addressId) {
        return paymentRepository.countByAddressIdAndPaidFalse(new ObjectId(addressId));
    }

    @Override
    public List<Payment> findUnpaid (String addressId) {
        return paymentRepository.findByAddressIdAndPaidFalse(new ObjectId(addressId), new Sort(Sort.Direction.ASC, "paymentDate"));
    }

    @Override
    public List<Payment> find (String addressId) {
        return paymentRepository.findByAddressId(new ObjectId(addressId), new Sort(Sort.Direction.ASC, "paymentDate"));
    }

    @Override
    public void setPaid (String id) {
        Payment payment = paymentRepository.findOne(new ObjectId(id));
        payment.setPaid(true);
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> get (String addressId, Month month, int year) {
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = LocalDate.of(year, month, monthStart.lengthOfMonth());
        return paymentRepository.findPaymentsBetweenDates(new ObjectId(addressId), monthStart, monthEnd);
    }

    @Override
    public List<Payment> get(String addressId, ServiceType type, int year) {
        LocalDate yearStart = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate yearEnd = LocalDate.of(year, Month.DECEMBER, 31);
        return paymentRepository.findPaymentsByService(new ObjectId(addressId), type, yearStart, yearEnd);
    }

    @Override
    public List<Payment> get(String addressId, int year) {
        LocalDate yearStart = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate yearEnd = LocalDate.of(year, Month.DECEMBER, 31);
        return paymentRepository.findPayments(new ObjectId(addressId), yearStart, yearEnd);
    }
}
