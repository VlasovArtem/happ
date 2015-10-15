package com.household.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.household.persistence.StatisticRepository;
import com.household.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by artemvlasov on 15/10/15.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public JsonNode getUnpaidStat(String addressId) {
        return statisticRepository.getUnpaidStatistic(addressId);
    }
}
