package com.household.service.impl;

import com.household.entity.City;
import com.household.entity.Street;
import com.household.persistence.StreetRepository;
import com.household.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by artemvlasov on 13/10/15.
 */
@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    private StreetRepository repository;

    @Override
    public List<Street> searchStreet(String text, String city) {
        return repository.searchStreet(text, city);
    }
}
