package com.household.utils.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by artemvlasov on 11/10/15.
 */
public class LocaldDateToDateConverter implements Converter<LocalDate, Date> {
    @Override
    public Date convert(LocalDate source) {
        return Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
