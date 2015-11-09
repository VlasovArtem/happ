package com.household.controller;

import com.household.entity.enums.MeterType;
import com.household.entity.enums.ServiceTypeAlias;
import com.household.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/search")
    public ResponseEntity search(@RequestParam String city,
                                 @RequestParam String type,
                                 @RequestParam(required = false) String subtype) {
        return ResponseEntity.ok(service.search(city, type, subtype));
    }

    @RequestMapping("/get/{apartmentId}")
    public ResponseEntity getServices (@PathVariable String apartmentId) {
        return ResponseEntity.ok(service.getAll(apartmentId));
    }

    @RequestMapping("/get/{apartmentId}/services")
    public ResponseEntity getApartmentServices (@PathVariable String apartmentId) {
        return ResponseEntity.ok(service.findPaymentsServiceIds(apartmentId));
    }

    @RequestMapping("/get/types")
    public ResponseEntity getServiceTypes() {
        return ResponseEntity.ok(service.getServiceTypes());
    }

    @RequestMapping("/get/subtypes")
    public ResponseEntity getServiceSubtypes() {
        return ResponseEntity.ok(service.getServiceSubtypes());
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
