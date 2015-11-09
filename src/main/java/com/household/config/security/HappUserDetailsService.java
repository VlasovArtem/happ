package com.household.config.security;

import com.household.entity.User;
import com.household.entity.enums.UserRole;
import com.household.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by artemvlasov on 21/10/15.
 */
@Component
public class HappUserDetailsService implements UserDetailsService {
    private final int PROJECT_AUTHORITIES_COUNT = UserRole.values().length;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginData) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.loginUser(loginData));
        Set<GrantedAuthority> authorities = new HashSet<>(PROJECT_AUTHORITIES_COUNT);
        if(user.isPresent()) {
            if(UserRole.ADMIN.equals(user.get().getRole())) {
                authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
            }
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.name()));
        } else {
            throw new UsernameNotFoundException("User with login data is not found or was deleted");
        }
        return new HappUserDetails(user.get().getLogin(), user.get().getPassword(), authorities, user.get().getId());
    }
}
