package com.household.utils.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by artemvlasov on 28/10/15.
 */
public class LocalDateConverter {
    public static LocalDate currentMonthStart() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate currentMonthEnd() {
        return LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    }
}
