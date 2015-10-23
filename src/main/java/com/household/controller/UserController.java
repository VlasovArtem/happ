package com.household.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.User;
import com.household.service.UserService;
import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by artemvlasov on 21/10/15.
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public void login (@RequestParam String loginData,
                       @RequestParam String password,
                       @RequestParam boolean rememberMe) {
    }

    @RequestMapping(value = "/registration", consumes = APPLICATION_JSON_VALUE, method = POST)
    @ResponseStatus(OK)
    public void registration (@RequestBody User user) {
        userService.registration(user);
    }

    @RequestMapping(value = "/logout", method = POST)
    public void logout () {}

    @RequestMapping(value = "/authentication", method = GET)
    public ResponseEntity authentication() {
        return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("role", userService.authentication()
                .toString()));
    }

//    @RequestMapping(value = "/authentication/info", method = GET)
//    public ResponseEntity authenticationAccountInfo() {
//
//    }
}
