package com.household.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "payments")
public class Payment {
    @Id
    private ObjectId id;
    @CreatedDate
    private LocalDateTime localDateTime;
    @Embedded
    private Service service;
    private double paymentSum;
    private LocalDateTime paymentDate;
    private boolean paid;
    private List<Integer> prevMeters;
    private List<Integer> curMeters;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

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
