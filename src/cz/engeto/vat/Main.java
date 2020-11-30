package cz.engeto.vat;

// external JAR file for JSON operations

// https://github.com/stleary/JSON-java
// https://repo1.maven.org/maven2/org/json/json/20201115/json-20201115.jar

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        final String file_path = "resources/rates.json";
        final String json_url = "https://euvatrates.com/rates.json";

        HashMap<String,Rate> rates = Rate.fillRates(json_url);

        for (Rate r : rates.values()) {
            if (r.standard_rate>20) {
                System.out.println(r.country+" ("+r.standard_rate+"%)");
            }
        }

    }
}
