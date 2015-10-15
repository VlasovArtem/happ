package com.household.entity;

import com.household.entity.enums.MeterType;
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
    private String id;
    @CreatedDate
    private LocalDateTime createdDate;
    @Embedded
    private Service service;
    private String personalAccount;
    private MeterType meterType;
    private String description;
    @Embedded
    private Address address;
    private double paymentSum;
    private LocalDate paymentDate;
    private boolean paid;
    private int[] prevMeter;
    private int[] curMeter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
