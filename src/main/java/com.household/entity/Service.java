package com.household.entity;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by artemvlasov on 02/09/15.
 */
@Entity("service")
public class Service extends BaseEntity{
    private String name;
    private double[] rates;
    private int[] volumes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
