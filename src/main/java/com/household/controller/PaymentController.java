package com.household.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.Payment;
import com.household.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 06/10/15.
 */
@RestController
@RequestMapping("/rest/payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @RequestMapping(value = "/add", method = POST)
    @ResponseStatus(OK)
    public void add(@RequestBody Payment payment) {
        service.add(payment);
    }

    @RequestMapping(value = "/last/other", method = GET)
    public ResponseEntity lastWithTypeOther (@RequestParam(value = "apartId") String apartmentId, @RequestParam String
            serviceId) {
        return ResponseEntity.ok(service.getLastWithTypeOther(apartmentId, serviceId));
    }

    @RequestMapping(value = "/last", method = GET, produces = APPLICATION_JSON_VALUE)
    public Payment last(@RequestParam String apartmentId, @RequestParam String type) {
        return service.findLastPayment(apartmentId, type);
    }

    @RequestMapping(value = "/unpaid/count", method = GET)
    public ResponseEntity countUnpaid (@RequestParam String apartmentId) {
        return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("count", service.countUnpaid(apartmentId)));
    }

    @RequestMapping(value = "/unpaid", method = GET)
    public ResponseEntity unpaid (@RequestParam String apartmentId) {
        return ResponseEntity.ok(service.findUnpaid(apartmentId));
    }

    @RequestMapping(value = "/paid/{id}", method = PUT)
    @ResponseStatus(OK)
    public void paid (@PathVariable String id) {
        service.setPaid(id);
    }

    @RequestMapping(value = "/get/stat/month", method = GET)
    public ResponseEntity getPage (@RequestParam String apartmentId, @RequestParam int monthNum, @RequestParam int year) {
        return ResponseEntity.ok(service.get(apartmentId, Month.of(monthNum), year));
    }

    @RequestMapping(value = "/get/stat/service", method = GET)
    public ResponseEntity getPage (@RequestParam String apartmentId, @RequestParam String type, @RequestParam int year) {
        return ResponseEntity.ok(service.get(apartmentId, type, year));
    }

    @RequestMapping(value = "/get/stat/all", method = GET)
    public ResponseEntity getPage (@RequestParam String apartmentId, @RequestParam int year) {
        return ResponseEntity.ok(service.get(apartmentId, year));
    }

}
