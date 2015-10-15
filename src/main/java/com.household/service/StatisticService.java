package com.household.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by artemvlasov on 15/10/15.
 */
public interface StatisticService {
    JsonNode getUnpaidStat(String addressId);
}
