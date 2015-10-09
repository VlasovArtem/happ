package com.household.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.household.persistence")
@EnableMongoAuditing
public class AppConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "household";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI("mongodb://localhost"));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.household";
    }
}
