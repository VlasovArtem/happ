package com.household.service.impl;

import com.household.entity.CustomService;
import com.household.persistence.CustomServiceRepository;
import com.household.service.CustomServiceService;
import com.household.utils.validator.CustomServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by artemvlasov on 04/11/15.
 */
@Service
public class CustomServiceServiceImpl implements CustomServiceService {
    @Autowired
    private CustomServiceRepository customServiceRepository;

    @Override
    public void add(CustomService customService) {
        CustomServiceValidator.validate(customService);
        customServiceRepository.save(customService);
    }
}
