package com.household.persistence.custom;

import org.bson.types.ObjectId;

/**
 * Created by artemvlasov on 21/10/15.
 */
public interface ApartmentRepositoryCustom {
    long getUserApartmentsCount(ObjectId id);
}
