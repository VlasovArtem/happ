package com.household.persistence;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by artemvlasov on 23/10/15.
 */
public interface StatisticRepository {
    JsonNode getUnpaidStatistic(String apartmentId);
    JsonNode getAccountApartmentsStatisticByCurrentMonth(String ownerId) throws IOException;
    JsonNode getApartmentStatisticByMonth(String apartmentId, int month, int year);
}
