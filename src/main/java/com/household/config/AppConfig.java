package com.household.config;

import com.mongodb.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.household.persistence"})
@EnableMongoAuditing
public class AppConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return System.getenv("OPENSHIFT_APP_NAME");
    }

    @Override
    public Mongo mongo() throws Exception {
        String openshiftHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int openshiftPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
        ServerAddress serverAddress = new DBAddress(openshiftHost, openshiftPort, databaseName);
        MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(username, databaseName, password
                .toCharArray());
        return new MongoClient(serverAddress, Collections.singletonList(mongoCredential));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.household";
    }

}
