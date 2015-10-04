package com.household.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Controller
public class UIController {
    @RequestMapping({"/", "/apartment/**"})
    public String index() {
        return "index";
    }
}
