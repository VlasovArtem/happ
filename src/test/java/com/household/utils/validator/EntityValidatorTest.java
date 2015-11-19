package com.household.utils.validator;

import org.junit.Test;

import static com.household.utils.validator.EntityValidator.validate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by artemvlasov on 13/11/15.
 */
public class EntityValidatorTest {
    @Test
    public void validateTest() {
        assertTrue(validate("test", "[a-z]+"));
    }

    @Test
    public void validateWithNotMatchesPatternTest() {
        assertFalse(validate("test", "[0-9]+"));
    }

    @Test
    public void validateWithDataNullTest() {
        assertFalse(validate(null, "[0-9]+"));
    }

    @Test
    public void validateWithPatternNullTest() {
        assertFalse(validate("test", null));
    }
}
