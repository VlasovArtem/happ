package com.household.controller;

import com.household.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 15/10/15.
 */
@RestController
@RequestMapping("/rest/stat")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "/unpaid/sum", method = GET)
    public ResponseEntity getApartmentUnpaidSum(@RequestParam String addressId) {
        System.out.println(addressId);
        return ResponseEntity.ok(statisticService.getUnpaidStat(addressId));
    }
}
