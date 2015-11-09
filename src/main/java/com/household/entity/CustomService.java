package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by artemvlasov on 04/11/15.
 */
@Document(collection = "customservices")
@JsonAutoDetect
public class CustomService {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    private Service service;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
