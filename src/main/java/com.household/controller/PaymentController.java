package com.household.controller;

import com.household.entity.Address;
import com.household.entity.Payment;
import com.household.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
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

    @RequestMapping(value = "/last", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Payment last(@RequestBody Address address, @RequestParam(name = "service_name") String serviceName) {
        return service.findLastPayment(address, serviceName);
    }

}
