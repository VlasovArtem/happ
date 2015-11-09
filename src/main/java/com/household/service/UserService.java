package com.household.service;

import com.household.entity.User;
import com.household.entity.enums.UserRole;

/**
 * Created by artemvlasov on 21/10/15.
 */
public interface UserService {
    void registration (User user);
    UserRole authentication();
}
