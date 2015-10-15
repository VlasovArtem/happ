package com.household.entity;

import com.household.entity.enums.ServiceTypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by artemvlasov on 13/10/15.
 */
@Document(collection = "servicetypes")
public class ServiceType {
    private String name;
    private String alias;
    ServiceTypeAlias group;
    private List<Subtype> subtypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ServiceTypeAlias getGroup() {
        return group;
    }

    public void setGroup(ServiceTypeAlias group) {
        this.group = group;
    }

    public List<Subtype> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<Subtype> subtypes) {
        this.subtypes = subtypes;
    }
}
