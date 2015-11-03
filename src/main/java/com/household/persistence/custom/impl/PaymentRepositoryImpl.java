package com.household.persistence.custom.impl;

import com.household.entity.Payment;
import com.household.entity.Service;
import com.household.persistence.custom.PaymentRepositoryCustom;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 28/10/15.
 */
@Repository
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Search payments associated with apartment form the last month
     * @param apartmentId
     * @return
     */
    @Override
    public List<Payment> findLastOtherPayments(String apartmentId) {
        LocalDate paymentSearchStartDate = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        return mongoTemplate.find(
                Query.query(
                        Criteria.where("id").in(mongoTemplate.aggregate(
                                newAggregation(
                                        match(
                                                Criteria.where("createdDate").gte(paymentSearchStartDate)
                                                        .and("service.type.alias").is("other")
                                                        .and("apartmentId").is(apartmentId)),
                                        sort(Sort.Direction.DESC, "createdDate"),
                                        group("personalAccount").first("id").as("paymentId"),
                                        project("paymentId").andExclude("_id")
                                ), Payment.class, DBObject.class).getMappedResults().stream().map(ob -> (ObjectId) ob.get("paymentId")).collect(Collectors.toList()))),
                Payment.class);
    }

    /**
     * Find all payment distinct services for particular apartment
     * @param apartmentId id of apartment
     * @return List of ids of the services
     */
    @Override
    public List<String> findApartmentPaymentServices(String apartmentId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("apartmentId").is("56113e02ad6d064ae69bd867")),
                group("service.id").first("service.id").as("id"),
                project("id").andExclude("_id")
        );
        return mongoTemplate.aggregate(aggregation, Payment.class, DBObject.class).getMappedResults().stream()
                .map(dbObject -> dbObject.get("id").toString()).collect(Collectors.toList());
    }
}
