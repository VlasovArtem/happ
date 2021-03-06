package com.household.service.impl;

import com.household.entity.User;
import com.household.entity.enums.UserRole;
import com.household.persistence.UserRepository;
import com.household.service.UserService;
import com.household.utils.exception.UserRegistrationException;
import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.household.utils.validator.UserValidator.validate;

/**
 * Created by artemvlasov on 21/10/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registration(User user) {
        validate(Optional.ofNullable(user).orElseThrow(() -> new UserRegistrationException("Person cannot be null")), false);
        if(userRepository.countByLoginOrEmail(user.getLogin(), user.getEmail()) > 1) {
            throw new UserRegistrationException("User with login or email is already exists");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    @Override
    public UserRole authentication() {
        if(AuthenticatedUserPrincipalUtil.containAuthorities(UserRole.ADMIN)) {
            return UserRole.ADMIN;
        } else {
            return UserRole.USER;
        }
    }
}
