package com.household;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.household.entity.Payment;
import com.household.entity.Service;
import com.household.entity.enums.ServiceTypeAlias;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 15/10/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) throws IOException {
        MongoOperations mongoTemplate = new MongoTemplate(new MongoClient(), "household");
        System.out.println(mongoTemplate.find(Query.query(Criteria.where("city.alias").is("Odessa").and("type.subtypes").exists(true)
                .and("type.subtypes.alias").is("tel")), Service.class).size());
    }
//    public static void main(String[] args) throws IOException {
//        MongoOperations mongoTemplate = new MongoTemplate(new MongoClient(), "household");
//        Aggregation aggregation = newAggregation(
//                match(Criteria.where("service.type.group").is(ServiceTypeAlias.OTHER)),
//                unwind("service.type.subtypes"),
//                group("service.type.subtypes.alias").first("service").as("service"),
//                project("service").andExclude("_id")
//        );
//        mongoTemplate.aggregate(aggregation, Payment.class, String.class).getMappedResults().stream().forEach(service -> {
//            try {
//                new ObjectMapper().readTree(service.getBytes()).fields().forEachRemaining(stringJsonNodeEntry -> {
//                    String value = stringJsonNodeEntry.getValue().toString().replace("_id", "id").replace
//                            ("\"city\":{\"id\"", "\"city\":{\"name\"").replace("\"subtypes\":{\"alias\"",
//                            "\"subtypes\":[{\"alias\"").replace("},\"alias\":\"other\"", "}],\"alias\":\"other\"");
//                    try {
//                        mongoTemplate.save(new ObjectMapper().readValue(value.getBytes(), Service.class));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
}
