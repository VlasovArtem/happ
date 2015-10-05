package com.household.service;

import com.household.entity.Service;
import com.household.entity.ServiceType;

import java.util.List;

/**
 * Created by artemvlasov on 05/10/15.
 */
public interface ServiceService {
    List<Service> getAll(String city, ServiceType type);
    List<ServiceType> getServiceTypes();
}
