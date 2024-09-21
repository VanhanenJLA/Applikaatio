package org.tuni.assignment.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.tuni.assignment.models.ElectricityPriceData;
import org.tuni.assignment.utils.Klinu;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SahkonHintaService {
    private static final String API_URL = "https://www.sahkohinta-api.fi/api/v1/halpa";

    public List<ElectricityPriceData> fetchElectricityPrices(LocalDate date) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("tunnit", "24"); // Number of hours to retrieve
        params.put("tulos", "sarja"); // Result format
        params.put("aikaraja", date.toString()); // Date in YYYY-MM-DD format

        String response = Klinu.sendGetRequest(API_URL, params);

        // Parse JSON response
        Gson gson = new Gson();
        JsonArray dataArray = gson.fromJson(response, JsonArray.class);

        List<ElectricityPriceData> priceDataList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject data = dataArray
                    .get(i)
                    .getAsJsonObject();
            String timestampString = data
                    .get("aikaleima_suomi")
                    .getAsString();
            LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);

            double price = Double.parseDouble(data
                    .get("hinta")
                    .getAsString());
            priceDataList.add(new ElectricityPriceData(timestamp, price));
        }

        return priceDataList;
    }
}
