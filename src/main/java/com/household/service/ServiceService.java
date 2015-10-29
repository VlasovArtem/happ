package com.household.service;

import com.household.entity.Service;
import com.household.entity.ServiceType;
import com.household.entity.enums.ServiceTypeAlias;

import java.util.List;

/**
 * Created by artemvlasov on 05/10/15.
 */
public interface ServiceService {
    List<Service> getAll(String city, String type);
    List<ServiceType> getServiceTypes();
    List<Service> getAll(String apartmentId);
    List<Service> search (String city, String type, String subtype);
}
