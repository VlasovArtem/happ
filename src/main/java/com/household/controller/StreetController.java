package com.household.controller;

import com.household.entity.City;
import com.household.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 13/10/15.
 */
@RestController
@RequestMapping("/rest/street")
public class StreetController {

    @Autowired
    private StreetService service;

    @RequestMapping(value = "/search", method = GET)
    public ResponseEntity search (@RequestParam String text, @RequestParam String city) {return ResponseEntity.status(HttpStatus.OK).body(service.searchStreet(text, city));
    }

}
