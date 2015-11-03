package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by artemvlasov on 15/10/15.
 */
@JsonAutoDetect
public class Subtype {
    private String alias;
    private String name;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subtype{" +
                "alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
