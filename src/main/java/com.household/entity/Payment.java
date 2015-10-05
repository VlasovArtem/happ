package com.household.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.PrePersist;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "payments")
public class Payment {
    @Id
    private ObjectId id;
    @CreatedDate
    private LocalDateTime createdDate;
    @Embedded
    @NotSaved
    private Service service;
    private double paymentSum;
    private LocalDateTime paymentDate;
    private boolean paid;
    private int prevMeter;
    private int curMeter;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public int getPrevMeter() {
        return prevMeter;
    }

    public void setPrevMeter(int prevMeter) {
        this.prevMeter = prevMeter;
    }

    public int getCurMeter() {
        return curMeter;
    }

    public void setCurMeter(int curMeter) {
        this.curMeter = curMeter;
    }
}
