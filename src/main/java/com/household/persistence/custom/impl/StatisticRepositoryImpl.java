package com.household.persistence.custom.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.Apartment;
import com.household.entity.Payment;
import com.household.persistence.StatisticRepository;
import com.household.utils.converter.LocalDateConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.household.utils.converter.LocalDateConverter.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 15/10/15.
 */
@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public JsonNode getUnpaidStatistic(String apartmentId) {
        Aggregation aggregation = newAggregation(
                match(new Criteria()
                        .andOperator(
                                Criteria.where("apartmentId").is(apartmentId),
                                Criteria.where("paid").is(false))),
                group().sum("paymentSum").as("unpaidSum").count().as("unpaid")
        );
        try {
            String object = mongoTemplate.aggregate(aggregation, Payment.class, String.class).getUniqueMappedResult();
            if(Objects.nonNull(object)) {
                return new ObjectMapper().readTree(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonNode getAccountApartmentsStatisticByCurrentMonth(String ownerId) throws IOException {
        Aggregation apartments = newAggregation(
                match(Criteria.where("ownerId").is(ownerId)),
                project("ownerId")
        );
        List<String> apartmentsId = mongoTemplate.aggregate(apartments, Apartment.class, Apartment.class).getMappedResults()
                .stream()
                .map(Apartment::getId).collect(Collectors.toList());
        Aggregation payments = newAggregation(
                match(Criteria.where("apartmentId").in(apartmentsId).and("paid").is(false)),
                group("apartmentId").sum("paymentSum").as("apartmentUnpaidSum").count().as("paymentCount"),
                group().sum("apartmentUnpaidSum").as("totalPaymentSum").count().as("apartmentsCount").sum
                        ("paymentCount").as("totalPaymentsCount")
        );
        String result = mongoTemplate.aggregate(payments, Payment.class, String.class).getUniqueMappedResult();
        if(Objects.nonNull(result)) {
            JsonNode mapper = new ObjectMapper().readTree(result);
            mapper.fields().forEachRemaining(stringJsonNodeEntry -> {
                if ("apartmentsCount".equals(stringJsonNodeEntry.getKey())) {
                    if (stringJsonNodeEntry.getValue().asInt() != apartmentsId.size()) {
                        stringJsonNodeEntry.setValue(JsonNodeFactory.instance.numberNode(apartmentsId.size()));
                    }
                }
            });
            return mapper;
        }
        return null;
    }

    public JsonNode getApartmentStatisticByMonth(String apartmentId, int month, int year) {
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
                                Criteria.where("paymentDate").gte(currentMonthStart()),
                                Criteria.where("paymentDate").lte(currentMonthEnd()),
                                Criteria.where("apartmentId").is(apartmentId))),
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
