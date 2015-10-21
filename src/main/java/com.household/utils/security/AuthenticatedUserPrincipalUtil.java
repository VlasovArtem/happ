package com.household.utils.security;

import com.household.config.security.HappUserDetails;
import com.household.entity.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by artemvlasov on 21/10/15.
 */
public class AuthenticatedUserPrincipalUtil {
    public static Optional<HappUserDetails> getAuthenticationPrincipal() {
        if(SecurityContextHolder.getContext() != null) {
            Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
            if(authentication.isPresent()) {
                if(authentication.get().isAuthenticated()) {
                    if(authentication.get().getPrincipal() instanceof HappUserDetails) {
                        return Optional.of((HappUserDetails) authentication.get().getPrincipal());
                    }
                }
            }
        }
        return Optional.empty();
    }
    public static boolean containAuthorities(UserRole... roles) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(p -> Arrays.asList(roles).contains(UserRole.valueOf(p.getAuthority())));
    }
}
