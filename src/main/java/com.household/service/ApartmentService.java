package com.household.service;

import com.household.entity.Apartment;

import java.util.List;

/**
 * Created by artemvlasov on 03/09/15.
 */
public interface ApartmentService {
    Apartment getApartment(String id);
    List<Apartment> getAll();
    void add(Apartment apartment);
}
