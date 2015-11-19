package com.household.config;

import com.household.config.security.SpringSecurityAuditorAware;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.household.persistence"})
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

    @Bean
    public AuditorAware<String> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
