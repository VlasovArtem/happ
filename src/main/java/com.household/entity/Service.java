package com.household.entity;

import com.household.entity.enums.ServiceType;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Document(collection = "services")
public class Service {
    @Id
    private String id;
    private ServiceInformation serviceInformation;
    @Embedded
    private City city;
    @Indexed
    private String name;
    private ServiceType type;
    private double[] rates;
    private int[] volumes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceInformation getServiceInformation() {
        return serviceInformation;
    }

    public void setServiceInformation(ServiceInformation serviceInformation) {
        this.serviceInformation = serviceInformation;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public double[] getRates() {
        return rates;
    }

    public void setRates(double[] rates) {
        this.rates = rates;
    }

    public int[] getVolumes() {
        return volumes;
    }

    public void setVolumes(int[] volumes) {
        this.volumes = volumes;
    }
}
