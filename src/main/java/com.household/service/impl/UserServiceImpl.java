package com.household.service.impl;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.household.entity.User;
import com.household.entity.UserRole;
import com.household.persistence.ApartmentRepository;
import com.household.persistence.StatisticRepository;
import com.household.persistence.UserRepository;
import com.household.service.UserService;
import com.household.utils.exception.UserRegistrationException;
import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(AuthenticatedUserPrincipalUtil.containAuthorities(UserRole.ADMIN)) {
            return UserRole.ADMIN;
        } else {
            return UserRole.USER;
        }
    }

    @Override
    public ObjectNode authenticationAccountInfo() {
        ObjectNode info = JsonNodeFactory.instance.objectNode();
        return null;
    }
}
