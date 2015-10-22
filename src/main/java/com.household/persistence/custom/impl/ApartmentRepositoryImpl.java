package com.household.persistence.custom.impl;

import com.household.entity.Apartment;
import com.household.persistence.custom.ApartmentRepositoryCustom;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;

/**
 * Created by artemvlasov on 21/10/15.
 */
@Component
public class ApartmentRepositoryImpl implements ApartmentRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long getUserApartmentsCount(ObjectId id) {
        DBObject dbObject = new BasicDBObject("owner",
                new BasicDBObject("$ref", "users")
                        .append("$id", id));
        return mongoTemplate.count(new BasicQuery(dbObject), Apartment.class);
    }
}
