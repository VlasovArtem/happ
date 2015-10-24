package com.household.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.Apartment;
import com.household.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by artemvlasov on 03/09/15.
 */
@RestController
@RequestMapping("/rest/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentService service;

    @RequestMapping("/get/all")
    public List<Apartment> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, method = POST)
    @ResponseStatus(CREATED)
    public void add(@RequestBody Apartment apartment) {
        service.add(apartment);
    }

    @RequestMapping(value = "/count", method = GET)
    public ResponseEntity count () {
        return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("count", service.count()));
    }
}
