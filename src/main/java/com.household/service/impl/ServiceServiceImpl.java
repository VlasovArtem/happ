package com.household.service.impl;

import com.household.entity.Service;
import com.household.entity.ServiceType;
import com.household.entity.enums.ServiceTypeAlias;
import com.household.persistence.ServiceRepository;
import com.household.persistence.ServiceTypeRepository;
import com.household.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by artemvlasov on 05/10/15.
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<Service> getAll(String city, String type) {
        return serviceRepository.findByCityAliasAndTypeAlias (city, type);
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        return serviceTypeRepository.findAll();
    }
}
