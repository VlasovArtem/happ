package com.household.utils.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by artemvlasov on 28/10/15.
 */
public class LocalDateConverter {
    public static Date localDateConverter(int year, int month, int date) {
        return Date.from(LocalDate.of(year, month, date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date currentMonthStart() {
        return localDateConverter(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
    }

    public static Date currentMonthEnd() {
        return localDateConverter(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now()
                .lengthOfMonth());
    }

    public static Date localDateConverter(LocalDate date) {
        return localDateConverter(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
