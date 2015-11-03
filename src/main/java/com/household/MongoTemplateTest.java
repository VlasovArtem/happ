package com.household;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

/**
 * Created by artemvlasov on 15/10/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) throws IOException {
        MongoOperations mongoTemplate = new MongoTemplate(new MongoClient(), "household");
    }
}
