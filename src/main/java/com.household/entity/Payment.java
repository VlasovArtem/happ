package com.household.entity;

import com.household.entity.enums.MeterType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.PrePersist;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "payments")
public class Payment {
    @Id
    private ObjectId id;
    private LocalDateTime createdDate;
    @Embedded
    @NotSaved
    private Service service;
    private MeterType meterType;
    private Address address;
    private double paymentSum;
    private LocalDate paymentDate;
    private boolean paid;
    private int[] prevMeter;
    private int[] curMeter;

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

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(double paymentSum) {
        this.paymentSum = paymentSum;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int[] getPrevMeter() {
        return prevMeter;
    }

    public void setPrevMeter(int[] prevMeter) {
        this.prevMeter = prevMeter;
    }

    public int[] getCurMeter() {
        return curMeter;
    }

    public void setCurMeter(int[] curMeter) {
        this.curMeter = curMeter;
    }

    @PrePersist
    public void prePersist() {
        if(meterType == null) {
            meterType = MeterType.ORIGINAL;
        }
        createdDate = LocalDateTime.now();
    }
}
