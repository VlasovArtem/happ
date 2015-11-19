package com.household.utils.validator;

import com.household.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.household.utils.validator.CustomServiceValidator.*;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 13/11/15.
 */
public class CustomServiceValidatorTest {
    private CustomService customService;

    @Before
    public void setup() {
        customService = new CustomService();
        Service service = new Service();
        service.setCity(new City("Odessa"));
        service.setName("Test");
        service.setServiceInformation(
                new ServiceInformation(300000, 26000000000000L));
        ServiceType serviceType = new ServiceType();
        serviceType.setName("other");
        Subtype subtype = new Subtype();
        subtype.setName("Internet");
        serviceType.setSubtypes(Collections.singletonList(subtype));
        service.setType(serviceType);
        customService.setService(service);
    }

    @Test
    public void validateTest() {
        assertTrue(validate(customService));
    }
}
