package com.household.controller;

import com.household.entity.City;
import com.household.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 04/10/15.
 */
@RestController
@RequestMapping("/rest/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @RequestMapping(path = "/get/all", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }
}
