package com.household.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by artemvlasov on 21/10/15.
 */
public class HappUserDetails extends User {

    private String id;

    public HappUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           String id) {
        super(username, password, authorities);
        this.id = id;
    }

    public HappUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getId() {
        return id;
    }
}
