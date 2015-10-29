package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.household.entity.enums.MeterType;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.PrePersist;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "payments")
@JsonAutoDetect
public class Payment {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdDate;
    private String apartmentId;
    @Embedded
    private Service service;
    private String personalAccount;
    private MeterType meterType;
    private String description;
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

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
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

    @Override
    public String toString() {
        return ", service=" + service;
    }
}
