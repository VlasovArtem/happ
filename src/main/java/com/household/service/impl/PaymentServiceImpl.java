package com.household.service.impl;

import com.household.entity.Payment;
import com.household.persistence.PaymentRepository;
import com.household.persistence.ServiceRepository;
import com.household.service.PaymentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

/**
 * Created by artemvlasov on 06/10/15.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void add (Payment payment) {
        com.household.entity.Service service = payment.getService();
        service.setServiceInformation(null);
        service.setRates(null);
        service.setVolumes(null);
        paymentRepository.save(payment);
    }

    @Override
    public Payment findLastPayment (String apartmentId, String type) {
        Pageable request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "paymentDate"));
        return paymentRepository.findByApartmentIdAndServiceTypeAlias(apartmentId, type, request).getContent().stream().findFirst().get();
    }

    @Override
    public long countUnpaid (String apartmentId) {
        return paymentRepository.countByApartmentIdAndPaidFalse(apartmentId);
    }

    @Override
    public List<Payment> findUnpaid (String apartmentId) {
        return paymentRepository.findByApartmentIdAndPaidFalse(apartmentId, new Sort(Sort.Direction.ASC, "paymentDate"));
    }

    @Override
    public List<Payment> find (String apartmentId) {
        return paymentRepository.findByApartmentId(apartmentId, new Sort(Sort.Direction.ASC, "paymentDate"));
    }

    @Override
    public void setPaid (String id) {
        Payment payment = paymentRepository.findOne(new ObjectId(id));
        payment.setPaid(true);
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> get (String apartmentId, Month month, int year) {
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = LocalDate.of(year, month, monthStart.lengthOfMonth());
        return paymentRepository.findPaymentsBetweenDates(apartmentId, monthStart, monthEnd);
    }

    @Override
    public List<Payment> get (String apartmentId, String type, int year) {
        LocalDate yearStart = LocalDate.of(year, Month.JANUARY, 1).minusDays(1);
        LocalDate yearEnd = LocalDate.of(year, Month.DECEMBER, 31).plusDays(1);
        return paymentRepository.findByApartmentIdAndServiceTypeAliasInAndPaymentDateBetween(apartmentId, type,
                yearStart, yearEnd);
    }

    @Override
    public List<Payment> get (String apartmentId, int year) {
        LocalDate yearStart = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate yearEnd = LocalDate.of(year, Month.DECEMBER, 31);
        return paymentRepository.findPayments(apartmentId, yearStart, yearEnd);
    }

    @Override
    public Payment getLastWithTypeOther(String apartmentId, String serviceId) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "createdDate"));
        List<Payment> payments = paymentRepository.findByApartmentIdAndServiceId(apartmentId, new ObjectId(serviceId),
                pageable);
        if(payments.size() != 0) {
            return payments.get(0);
        }
        return null;
    }
}
