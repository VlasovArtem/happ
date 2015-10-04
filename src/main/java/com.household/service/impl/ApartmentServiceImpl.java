package com.household.service.impl;

import com.household.entity.Apartment;
import com.household.persistence.ApartmentRepository;
import com.household.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public Apartment getApartment(String id) {
        return apartmentRepository.findOne(id);
    }

    @Override
    public List<Apartment> getAll() {
        return apartmentRepository.findAll();
    }

    @Override
    public void add(Apartment apartment) {
        apartmentRepository.save(apartment);
    }
}
