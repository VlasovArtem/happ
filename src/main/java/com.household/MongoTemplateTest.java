package com.household;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.household.entity.Apartment;
import com.household.entity.Payment;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 15/10/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) {
        MongoOperations mongoTemplate = new MongoTemplate(new MongoClient(), "household");
        Aggregation apartments = newAggregation(
                match(Criteria.where("ownerId").is("5627bc99ad6d068db81c99f5")),
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
        System.out.println(result);

    }
}
