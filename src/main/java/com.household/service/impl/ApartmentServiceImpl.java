package com.household.service.impl;

import com.household.entity.Apartment;
import com.household.entity.User;
import com.household.persistence.ApartmentRepository;
import com.household.service.ApartmentService;
import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.bson.types.ObjectId;
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
        if(apartmentRepository.countByAddressCityAndAddressStreetAndAddressHouseAndAddressApartment(
                apartment.getAddress().getCity(),
                apartment.getAddress().getStreet(),
                apartment.getAddress().getHouse(),
                apartment.getAddress().getApartment()) > 0) {
            throw new RuntimeException("Apartment already exists");
        }
        User user = new User();
        user.setId(AuthenticatedUserPrincipalUtil.getAuthenticationPrincipal().get().getId());
        apartment.setOwner(user);
        apartment.getAddress().setId(ObjectId.get().toString());
        apartmentRepository.save(apartment);
    }

    @Override
    public long count() {
        return apartmentRepository.getUserApartmentsCount(new ObjectId(AuthenticatedUserPrincipalUtil
                .getAuthenticationPrincipal().get().getId()));
    }
}
