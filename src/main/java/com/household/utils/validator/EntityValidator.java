package com.household.utils.validator;

import java.util.regex.Pattern;

/**
 * Created by artemvlasov on 21/10/15.
 */
public class EntityValidator {
    /**
     * Validate String data with Pattern
     * @param data Entity field data to validate
     * @param pattern Pattern that should data matches
     * @return boolean true and only true if data matches pattern
     */
    public static boolean validate(String data, String pattern) {
        return !(data == null || pattern == null) && Pattern.compile(pattern).matcher(data).matches();
    }
}
