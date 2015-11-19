package com.household.utils.validator;

/**
 * Created by artemvlasov on 21/10/15.
 */

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.household.entity.User;
import com.household.utils.exception.EntityValidationException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * {@code PersonValidator} validate name, login, password, email, phone number, description of
 * {@code Person}.
 * Return true if validate data is matches patterns. Throws {@code EntityValidationException} with error message if
 * validate data not matches pattern.
 */
public class UserValidator extends EntityValidator {

    /**
     * Validate {@code PersonValidator}
     * @param {@link User} Optional of {@code PersonValidator}
     * @return true if all validate data matches their patterns otherwise throw {@code EntityValidationException}
     */
    public static boolean validate(User user, boolean update) {
        if(Objects.isNull(user)) {
            return false;
        } else {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            Arrays.stream(PersonValidationInfo.values()).allMatch(dc -> {
                if(update && dc.equals(PersonValidationInfo.PASSWORD)) {
                    return true;
                }
                if (!validate(dc, user)) {
                    objectNode.put(dc.name().toLowerCase(), dc.getError());
                }
                return true;
            });
            if (objectNode.size() != 0) {
                throw new EntityValidationException(objectNode, "Person form contains invalid data");
            }
            return true;
        }
    }

    /**
     * Validate {@code PersonValidator} data
     * @param info Enum for one of validated data
     * @param user Validated object
     * @return true if validated data is matches pattern
     */
    private static boolean validate(PersonValidationInfo info, User user) {
        switch (info) {
            case FIRSTNAME:
                if(Objects.equals(user.getFirstname(), "")) {
                    user.setFirstname(null);
                }
                return user.getFirstname() == null ||
                        validate(user.getFirstname(), info.pattern);
            case LASTNAME:
                if(Objects.equals(user.getLastname(), "")) {
                    user.setLastname(null);
                }
                return user.getLastname() == null ||
                        validate(user.getLastname(), info.pattern);
            case LOGIN:
                return validate(user.getLogin(), info.pattern);
            case PASSWORD:
                return validate(user.getPassword(), info.pattern);
            case EMAIL:
                return EmailValidator.getInstance().isValid(user.getEmail());
            default:
                return false;
        }
    }

    /**
     * Enum contains error messages and patterns for validating data
     */
    public enum PersonValidationInfo {
        FIRSTNAME("First name should not contains any digits and length should be 3 - 25",
                "^[\\p{L}]{3,25}$"),
        LASTNAME("Last name should not contains any digits and length should be 2 - 25",
                "^[\\p{L} .'\\-]{2,25}$"),
        LOGIN("Login should contains next characters: a-z 0-9 _ -. And length should be 6 - 100",
                "^[A-Za-z0-9_\\- .]{6,100}$"),
        PASSWORD("Min length of password should be 8 and max 128",
                "^.{8,128}$"),
        EMAIL("Email is not matches standard pattern",
                "");
        private String error;
        private String pattern;

        PersonValidationInfo(String error, String pattern) {
            this.error = error;
            this.pattern = pattern;
        }

        public String getError() {
            return error;
        }

        public String getPattern() {
            return pattern;
        }
    }
}