package com.household;


import com.household.entity.Street;
import com.mongodb.MongoClient;
import org.apache.commons.io.FileUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by artemvlasov on 12/10/15.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        MongoOperations mo = new MongoTemplate(new MongoClient(), "household");
        File file = new File("Odessa-city-streets.txt");
        List<String> lines = FileUtils.readLines(file);
        Pattern withOutArcPattern = Pattern.compile(".+(?=\\s|^)|(?!\\s).+");
        Pattern withArcPattern = Pattern.compile("(.+(?=\\s\\())|(\\(.+)");
        for (String line : lines) {
            int test = 0;
            Street street = new Street();
            if (line.contains("(")) {
                StringBuilder streetType = new StringBuilder();
                Matcher matcher = withArcPattern.matcher(line);
                int count = 0;
                while (matcher.find()) {
                    if (count == 0) {
                        int nestedCount = 0;
                        Matcher nestedMatcher = withOutArcPattern.matcher(matcher.group());
                        while (nestedMatcher.find()) {
                            if (nestedCount == 0) {
                                street.setName(nestedMatcher.group());
                            } else {
                                streetType.append(nestedMatcher.group()).append(" ");
                            }
                            nestedCount++;
                        }
                    } else {
                        streetType.append(matcher.group());
                    }
                    count++;
                }
                street.setType(streetType.toString());
            } else {
                Matcher matcher = withOutArcPattern.matcher(line);
                int count = 0;
                while (matcher.find()) {
                    if(count == 0) {
                        street.setName(matcher.group());
                    } else {
                        street.setType(matcher.group());
                    }
                    count++;
                }
            }
            test++;
            System.out.println(street);
            if (test == 10) {
                System.exit(0);
            }
            mo.save(street);
        }
    }
}
