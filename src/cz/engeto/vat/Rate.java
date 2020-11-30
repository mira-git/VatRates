package cz.engeto.vat;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Rate {
    String code;
    String country;
    float standard_rate;
    float reduced_rate;
    Boolean parking_rate;

    public Rate(String code, String country, float standard_rate, float reduced_rate, Boolean parking_rate) {
        this.code = code;
        this.country = country;
        this.standard_rate = standard_rate;
        this.reduced_rate = reduced_rate;
        this.parking_rate = parking_rate;
    }

    public static HashMap<String,Rate> fillRates(String source) {

        HashMap<String,Rate> rates = new HashMap<String,Rate>();
        String json_str = "";
        try {
            Scanner scan;
            // choose source file or URL
            if (source.contains("http")) {
                URL url = new URL(source);
                scan = new Scanner(url.openStream());
            } else {
                File file = new File(source);
                scan = new Scanner(file);
            }
            while (scan.hasNext())
                json_str += scan.nextLine();
            scan.close();

            // parse
            JSONObject json_obj = new JSONObject(json_str);
            json_obj = json_obj.getJSONObject("rates");
            for (String k: json_obj.toMap().keySet()) {
                JSONObject r = (JSONObject) json_obj.get(k);

                Rate rate = new Rate(
                        k,
                        r.getString("country"),
                        r.getFloat("standard_rate"),
                        // float required for reduced_rate, false -> standard_rate
                        r.get("reduced_rate") instanceof Float ? r.getFloat("reduced_rate") : r.getFloat("standard_rate"),
                        // Boolean required for parking_rate, number -> true
                        !(r.get("parking_rate") instanceof Boolean)
                );
                rates.put(k,rate);
            }

        } catch (IOException | JSONException e){
            System.out.println(e.toString());
        }

        return rates;
    }
}
