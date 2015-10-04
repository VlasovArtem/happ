package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "apartments")
@JsonAutoDetect
public class Apartment {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdDate;
    @Indexed
    @Embedded
    private Address address;
    @DBRef
    private List<Payment> payments;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
