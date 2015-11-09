package com.household.config.security;

import com.household.utils.security.AuthenticatedUserPrincipalUtil;
import org.springframework.data.domain.AuditorAware;

/**
 * Created by artemvlasov on 04/11/15.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return AuthenticatedUserPrincipalUtil.getAuthenticationPrincipal().get().getId();
    }
}
