package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.mongodb.morphia.annotations.NotSaved;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by artemvlasov on 12/10/15.
 */
@Document(collection = "streets")
@JsonAutoDetect
public class Street {
    @Id
    private String id;
    @NotSaved
    private City city;
    private String name;
    private String type;

    public Street() {
    }

    public Street(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
