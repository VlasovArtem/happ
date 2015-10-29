package com.household.service.impl;

import com.household.entity.Service;
import com.household.entity.ServiceType;
import com.household.persistence.ApartmentRepository;
import com.household.persistence.ServiceRepository;
import com.household.persistence.ServiceTypeRepository;
import com.household.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

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
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public List<Service> getAll(String city, String type) {
        return serviceRepository.findByCityAliasAndTypeAlias (city, type);
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public List<Service> getAll(String apartmentId) {
        return serviceRepository.findByCityAlias(apartmentRepository.findApartmentCity(apartmentId).getAddress()
                .getCity().getAlias(), new Sort(Sort.Direction.DESC, "type.alias"));
    }

    @Override
    public List<Service> search(String city, String type, String subtype) {
        if(subtype == null) {
            return serviceRepository.findByCityAliasAndTypeAlias(city, type);
        } else {
            return serviceRepository.findByCityAliasTypeSubtypesAlias(city, subtype);
        }
    }
}
