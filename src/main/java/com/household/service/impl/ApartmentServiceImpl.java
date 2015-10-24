package com.household.service.impl;

import com.household.entity.Apartment;
import com.household.entity.User;
import com.household.persistence.ApartmentRepository;
import com.household.persistence.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    @Override
    public Apartment getApartment(String id) {
        return apartmentRepository.findOne(id);
    }

    @Override
    public List<Apartment> getAll() {
        return apartmentRepository.findByOwnerId(AuthenticatedUserPrincipalUtil
                .getAuthenticationPrincipal().get().getId());
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
        apartment.setOwnerId(AuthenticatedUserPrincipalUtil.getAuthenticationPrincipal().get().getId());
        Apartment savedApartment = apartmentRepository.save(apartment);
        User user = userRepository.findOne(AuthenticatedUserPrincipalUtil.getAuthenticationPrincipal().get().getId());
        user.getApartments().add(savedApartment);
        userRepository.save(user);
    }

    @Override
    public long count() {
        return apartmentRepository.countByOwnerId(AuthenticatedUserPrincipalUtil
                .getAuthenticationPrincipal().get().getId());
    }
}
