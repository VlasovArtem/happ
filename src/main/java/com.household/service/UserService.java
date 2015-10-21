package com.household.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.household.entity.User;
import com.household.entity.UserRole;

/**
 * Created by artemvlasov on 21/10/15.
 */
public interface UserService {
    void registration (User user);
    UserRole authentication();
    ObjectNode authenticationAccountInfo();
}
