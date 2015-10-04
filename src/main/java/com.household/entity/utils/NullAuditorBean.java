package com.household.entity.utils;

import org.springframework.data.domain.AuditorAware;

/**
 * Created by artemvlasov on 23/09/15.
 */
public class NullAuditorBean implements AuditorAware {
    @Override
    public Object getCurrentAuditor() {
        return null;
    }
}
