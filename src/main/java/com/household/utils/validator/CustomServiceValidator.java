package com.household.utils.validator;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.household.entity.CustomService;
import com.household.utils.exception.EntityValidationException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by artemvlasov on 04/11/15.
 */
public class CustomServiceValidator extends EntityValidator {
    /**
     * Validate {@code PersonValidator}
     * @param {@link User} Optional of {@code PersonValidator}
     * @return true if all validate data matches their patterns otherwise throw {@code EntityValidationException}
     */
    public static boolean validate(CustomService customService) {
        try {
            Optional.of(customService).ifPresent(p -> {
                if(Objects.nonNull(p.getService())) {
                    ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                    Arrays.stream(PersonValidationInfo.values()).allMatch(dc -> {
                        if (!validate(dc, p)) {
                            objectNode.put(dc.name().toLowerCase(), dc.getError());
                        }
                        return true;
                    });
                    if (objectNode.size() != 0) {
                        throw new EntityValidationException(objectNode, "Сервис содержит некорректные данные");
                    }
                } else {
                    throw new EntityValidationException(null, "Данные по сервису не может быть пустым.");
                }
            });
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Validate {@code PersonValidator} data
     * @param info Enum for one of validated data
     * @param customService Validated object
     * @return true if validated data is matches pattern
     */
    private static boolean validate(PersonValidationInfo info, CustomService customService) {
        switch (info) {
            case NAME:
                return validate(customService.getService().getName(), info.pattern);
            case CITY:
                return Objects.nonNull(customService.getService().getCity());
            case BANK_CODE:
                return !Objects.nonNull(customService.getService().getServiceInformation()) ||
                        validate(String.valueOf(customService.getService().getServiceInformation().getBankCode()), info.pattern);
            case CHECKING_ACCOUNT:
                return !Objects.nonNull(customService.getService().getServiceInformation()) ||
                        validate(String.valueOf(customService.getService().getServiceInformation().getCheckingAccount()), info.pattern);
            case SUBTYPE:
                if(Objects.nonNull(customService.getService().getType()) && Objects.nonNull(customService.getService
                        ().getType().getSubtypes())) {
                    if(customService.getService().getType().getSubtypes().size() > 0) {
                        return validate(customService.getService().getType().getSubtypes().get(0).getName(), info
                                .pattern);
                    }
                }
                return false;
        }
        return true;
    }

    /**
     * Enum contains error messages and patterns for validating data
     */
    public enum PersonValidationInfo {
        NAME("Имя сервиса должен содержать, только буквы и пробелы. Имя должно быть от 3 до 30 символов " +
                "длинной.",
                "^[а-яА-Яa-zA-z ]{3,30}$"),
        CITY("Поле город обязательно", ""),
        BANK_CODE("Диапазон значений кода банка равен 300000 - 399999",
                "3[0-9]{5}"),
        CHECKING_ACCOUNT("Расчётный счёт должне начинаться с 26 и быть длинной в 14 цифр",
                "26[0-9]{12}"),
        SUBTYPE("Имя типа сервиса должно содержать буквы или пробелы. Длинна должна быть между 3 до 100 символов",
                "^[а-яА-Яa-zA-z ]{3,100}$");
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
