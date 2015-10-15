package com.household.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.household.entity.Payment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * Created by artemvlasov on 15/10/15.
 */
@Repository
public class StatisticRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public JsonNode getUnpaidStatistic(String addressId) {
        Aggregation aggregation = newAggregation(
                match(new Criteria()
                        .andOperator(
                                Criteria.where("address._id").is(new ObjectId(addressId)),
                                Criteria.where("paid").is(false))),
                group().sum("paymentSum").as("unpaidSum").count().as("unpaid")
        );
        String object = mongoTemplate.aggregate(aggregation, Payment.class, String.class).getUniqueMappedResult();
        try {
            return new ObjectMapper().readTree(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonNode getAddressStatisticByMonth(String addressId, int month, int year) {
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = LocalDate.of(year, month, monthStart.lengthOfMonth());
        Date convertedMonthStart = Date.from(monthStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date convertedMonthEnd = Date.from(monthEnd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        DBObject dbObject =
                new BasicDBObject("$group",
                        new BasicDBObject("_id",
                                null)
                                .append("monthSum",
                                        new BasicDBObject("$sum", "$paymentSum"))
                                .append("unpaidMonthSum", new BasicDBObject(
                                        "$sum", new BasicDBObject(
                                        "$cond", new Object[]{new BasicDBObject(
                                        "$eq", new Object[]{
                                        "$paid", true}),
                                        0,
                                        "$paymentSum"
                                })))
                                .append("paidPayments", new BasicDBObject(
                                        "$sum", new BasicDBObject(
                                        "$cond", new Object[]{new BasicDBObject(
                                        "$eq", new Object[]{
                                        "$paid", true}),
                                        1,
                                        0
                                })))
                                .append("unpaidPayments", new BasicDBObject(
                                        "$sum", new BasicDBObject(
                                        "$cond", new Object[]{new BasicDBObject(
                                        "$eq", new Object[]{
                                        "$paid", true}),
                                        0,
                                        1
                                })))
                );
        Aggregation aggregation = newAggregation(
                match(new Criteria()
                        .andOperator(
                                Criteria.where("paymentDate").gte(convertedMonthStart),
                                Criteria.where("paymentDate").lte(convertedMonthEnd),
                                Criteria.where("address._id").is(new ObjectId(addressId)))),
                context -> {
                    return context.getMappedObject(dbObject);
                }
        );
        String object = mongoTemplate.aggregate(aggregation, Payment.class, String.class).getUniqueMappedResult();
        try {
            return new ObjectMapper().readTree(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
