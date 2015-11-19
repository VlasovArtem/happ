package com.household.utils.validator;

import com.household.entity.User;
import com.household.utils.exception.EntityValidationException;
import org.junit.Before;
import org.junit.Test;

import static com.household.utils.validator.UserValidator.validate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by artemvlasov on 13/11/15.
 */
public class UserValidatorTest {
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setLogin("testlogin");
        user.setEmail("testemail@mail.com");
        user.setPassword("testpassword");
    }

    @Test
    public void validateTest() {
        assertTrue(validate(user, false));
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidFirstnameTest() {
        user.setFirstname("1valid");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidLastnameTest() {
        user.setLastname("|valid");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidLoginTest() {
        user.setLogin("test");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidEmailTest() {
        user.setEmail("invalidemail");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidPasswordTest() {
        user.setPassword("1234");
        validate(user, false);
    }

    @Test
    public void validateWithNullFirstnameTest() {
        user.setFirstname(null);
        assertTrue(validate(user, false));
    }

    @Test
    public void validateWithNullLastnameTest() {
        user.setLastname(null);
        assertTrue(validate(user, false));
    }

    @Test
    public void validateWithUpdateFlagTest() {
        user.setPassword(null);
        assertTrue(validate(user, true));
    }

    @Test
    public void validateWithNullDataTest() {
        assertFalse(validate(null, false));
    }

    @Test
    public void validateWithEmptyFirstnameTest() {
        user.setFirstname("");
        assertTrue(validate(user, false));
    }

    @Test
    public void validateWithEmptyLastnameTest() {
        user.setLastname("");
        assertTrue(validate(user, false));
    }
}
