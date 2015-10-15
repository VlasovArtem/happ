package com.household.utils.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by artemvlasov on 11/10/15.
 */
public class DateToLocalDateConverter implements Converter<Date, LocalDate> {
    @Override
    public LocalDate convert(Date source) {
        return LocalDate.from(source.toInstant());
    }
}
