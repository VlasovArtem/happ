package com.household;

import com.household.entity.User;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by artemvlasov on 15/10/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) {
//                MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
//                LocalDate today = LocalDate.now();
//                LocalDateTime firstStart = LocalDateTime.now();
//                System.err.println("Start: " + firstStart);
//                final TestStat testStat = new TestStat();
//                mo.find(Query.query(
//                        Criteria.where("address.id").is(new ObjectId("561cd909ad6d06d4c3bb0047"))
//                ), Payment.class).stream().forEach(p -> {
//                        if(p.isPaid()) {
//                                testStat.setPaidPayments(testStat.getPaidPayments() + 1);
//                        } else {
//                                testStat.setUnpaidPayments(testStat.getUnpaidPayments() + 1);
//                                testStat.setUnpaidMonthSum(testStat.getUnpaidMonthSum() + p.getPaymentSum());
//                        }
//                        testStat.setMonthSum(testStat.getMonthSum() + p.getPaymentSum());
//                });

//                LocalDateTime firstEnd = LocalDateTime.now();
//                System.err.println("End: " + firstEnd);
//                System.err.println("Duration: " + Duration.between(firstStart, firstEnd).getNano());

        MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
        User user = mo.find(Query.query(
                new Criteria().andOperator(
                        new Criteria().orOperator(
                                Criteria.where("login").is("vlasovartem"),
                                Criteria.where("email").is("vlasovartem")),
                        Criteria.where("deleted").is(false))), User.class).get(0);
        System.out.println(user);
//                Aggregation aggregation = newAggregation(
//                        match(new Criteria()
//                                .andOperator(
//                                        Criteria.where("address._id").is(new ObjectId("561cd909ad6d06d4c3bb0047")),
//                                        Criteria.where("paid").is(false))),
//                        group().sum("paymentSum").as("unpaidSum")
//                );
//                String object = mo.aggregate(aggregation, Payment.class, String.class).getUniqueMappedResult();
//                JsonNode mapper = null;
//                try {
//                        mapper = new ObjectMapper().readTree(object);
//                        System.out.println(mapper.findValue("unpaidSum").asDouble());
//                } catch (IOException e) {
//                        e.printStackTrace();
//                }
    }
}
