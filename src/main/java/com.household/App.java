package com.household;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.household.entity.*;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artemvlasov on 03/09/15.
 */
public class App {
    public static void main(String[] args) throws JsonProcessingException {
        MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
        List<Service> services = new ArrayList<>();

        services.add(createService(new ServiceInformation(328845, 2603830133777l),
                new City("Odessa"),
                "Ugniy DES",
                ServiceType.ELECTRICITY,
                new double[] {45.6, 78.9, 147.9},
                new int[] {100, 500}));
        services.add(createService(new ServiceInformation(320478, 26034111270720l),
                new City("Odessa"),
                "Odessagas",
                ServiceType.GAS,
                new double[] {3.6, 7.188},
                new int[] {200}));
        services.add(createService(new ServiceInformation(328168, 26003264781l),
                new City("Odessa"),
                "Viliams Oasis",
                ServiceType.MAINTENANCE,
                new double[] {2.76},
                null));
        services.add(createService(new ServiceInformation(307123, 26005020119702l),
                new City("Odessa"),
                "Infox",
                ServiceType.WATER,
                new double[] {9.36},
                null));
        services.add(createService(null, new City("Odessa"), "ICN", ServiceType.INTERNET, null, null));
        services.stream().forEach(mo::save);
    }

    public static Service createService(ServiceInformation info, City city, String name, ServiceType type, double[] rates, int[] volumes) {
        Service service = new Service();
        service.setCity(city);
        service.setName(name);
        service.setRates(rates);
        service.setServiceInformation(info);
        service.setType(type);
        service.setVolumes(volumes);
        return service;
    }
}
