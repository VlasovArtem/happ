package com.household.persistence;

import com.household.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by artemvlasov on 21/10/15.
 */
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'$and' : [{'$or' : [{'login' : ?0}, {'email' : ?0}]}, {'deleted' : false}]}")
    User loginUser (String loginData);
    long countByLoginOrEmail(String login, String email);
}
