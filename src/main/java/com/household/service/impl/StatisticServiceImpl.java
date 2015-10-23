package com.household.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.household.persistence.StatisticRepository;
import com.household.service.StatisticService;
import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by artemvlasov on 15/10/15.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public JsonNode getUnpaidStat(String apartmentId) {
        return statisticRepository.getUnpaidStatistic(apartmentId);
    }

    @Override
    public JsonNode getAccountApartmentStat() {
        try {
            return statisticRepository.getAccountApartmentsStatisticByCurrentMonth(AuthenticatedUserPrincipalUtil
                    .getAuthenticationPrincipal().get().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
