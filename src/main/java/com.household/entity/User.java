package com.household.entity;

import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * Created by artemvlasov on 03/09/15.
 */
@Document(collection = "users")
public class User extends BaseEntity {
    private String name;
    private String email;
    private String login;
    private String password;
    @Embedded
    private Set<Apartment> apartments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }
}
