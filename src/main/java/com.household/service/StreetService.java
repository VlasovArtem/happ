package com.household.service;

import com.household.entity.City;
import com.household.entity.Street;

import java.util.List;

/**
 * Created by artemvlasov on 13/10/15.
 */
public interface StreetService {
    List<Street> searchStreet (String text, String city);
}
