package com.household.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by artemvlasov on 15/10/15.
 */
public interface StatisticService {
    JsonNode getUnpaidStat (String apartmentId);
    JsonNode getAccountApartmentStat ();
}
