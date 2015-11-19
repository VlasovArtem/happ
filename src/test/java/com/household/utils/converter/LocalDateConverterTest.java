package com.household.utils.converter;

import org.junit.Test;

import java.time.LocalDate;

import static com.household.utils.converter.LocalDateConverter.currentMonthEnd;
import static com.household.utils.converter.LocalDateConverter.currentMonthStart;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by artemvlasov on 13/11/15.
 */
public class LocalDateConverterTest {
    @Test
    public void currentMonthStartTest() {
        assertThat(currentMonthStart().getDayOfMonth(), is(1));
    }

    @Test
    public void currentMonthEndTest() {
        assertThat(currentMonthEnd().getDayOfMonth(), is(LocalDate.now().lengthOfMonth()));
    }

}
