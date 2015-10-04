package com.household.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by artemvlasov on 02/09/15.
 */
public class Payment extends BaseEntity {
    @Embedded
    private Service service;
    private double paymentSum;
    private LocalDateTime paymentDate;
    private boolean paid;
    private List<Integer> prevMeters;
    private List<Integer> curMeters;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(double paymentSum) {
        this.paymentSum = paymentSum;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<Integer> getPrevMeters() {
        return prevMeters;
    }

    public void setPrevMeters(List<Integer> prevMeters) {
        this.prevMeters = prevMeters;
    }

    public List<Integer> getCurMeters() {
        return curMeters;
    }

    public void setCurMeters(List<Integer> curMeters) {
        this.curMeters = curMeters;
    }
}
