package com.household.controller;

import com.household.entity.CustomService;
import com.household.service.CustomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by artemvlasov on 04/11/15.
 */
@RequestMapping("/rest/custom/service")
@RestController
public class CustomServiceController {
    @Autowired
    private CustomServiceService customServiceService;

    @RequestMapping(value = "/add", method = POST)
    public void add (@RequestBody CustomService customService) {
        customServiceService.add(customService);
    }
}
