package com.household.controller;

import com.household.entity.enums.MeterType;
import com.household.entity.enums.ServiceTypeAlias;
import com.household.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by artemvlasov on 05/10/15.
 */
@RestController
@RequestMapping("/rest/service")
public class ServiceController {
    @Autowired
    private ServiceService service;

    @RequestMapping("/get/types")
    public ResponseEntity getServiceTypes() {
        return ResponseEntity.ok(service.getServiceTypes());
    }

    @RequestMapping("/get/meters")
    public ResponseEntity getMeterTypes() {
        return ResponseEntity.ok(MeterType.values());
    }

    @RequestMapping("/get/all")
    public ResponseEntity getAll(@RequestParam String city, @RequestParam String type) {
        return ResponseEntity.ok(service.getAll(city, type));
    }
}
