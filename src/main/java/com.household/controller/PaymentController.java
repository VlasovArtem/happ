package com.household.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.Payment;
import com.household.entity.enums.ServiceTypeAlias;
import com.household.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

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

    @RequestMapping(value = "/last", method = GET, produces = APPLICATION_JSON_VALUE)
    public Payment last(@RequestParam String addressId, @RequestParam String type) {
        return service.findLastPayment(addressId, type);
    }

    @RequestMapping(value = "/unpaid/count", method = GET)
    public ResponseEntity countUnpaid (@RequestParam String addressId) {
        return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("count", service.countUnpaid(addressId)));
    }

    @RequestMapping(value = "/unpaid", method = GET)
    public ResponseEntity unpaid (@RequestParam String addressId) {
        return ResponseEntity.ok(service.findUnpaid(addressId));
    }

    @RequestMapping(value = "/paid/{id}", method = PUT)
    @ResponseStatus(OK)
    public void paid (@PathVariable String id) {
        service.setPaid(id);
    }

    @RequestMapping(value = "/get/stat/month", method = GET)
    public ResponseEntity getPage (@RequestParam String addressId, @RequestParam int monthNum, @RequestParam int year) {
        return ResponseEntity.ok(service.get(addressId, Month.of(monthNum), year));
    }

    @RequestMapping(value = "/get/stat/service", method = GET)
    public ResponseEntity getPage (@RequestParam String addressId, @RequestParam String type, @RequestParam int year) {
        return ResponseEntity.ok(service.get(addressId, type, year));
    }

    @RequestMapping(value = "/get/stat/all", method = GET)
    public ResponseEntity getPage (@RequestParam String addressId, @RequestParam int year) {
        return ResponseEntity.ok(service.get(addressId, year));
    }

}
