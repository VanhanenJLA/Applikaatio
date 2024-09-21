package org.tuni.assignment.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.tuni.assignment.models.ElectricityPriceData;
import org.tuni.assignment.utils.Klinu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FingridService {
    private static final String API_URL = "https://data.fingrid.fi/api/datasets/245/data";
    private static final String API_KEY = ""; // Replace with your API key

    public List<ElectricityPriceData> fetchElectricityPrices(LocalDateTime startDate, LocalDateTime endDate) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("start_time", startDate.toString());
        params.put("end_time", endDate.toString());
        params.put("format", "json");
        params.put("pageSize", "100");
        params.put("sortOrder", "asc");

        var response = Klinu.sendGetRequest(API_URL, params, Optional.of(API_KEY));

        // Parse JSON response
        var gson = new Gson();
        var dataObject = gson.fromJson(response, JsonObject.class);
        var dataArray = gson.fromJson(response, JsonArray.class);

        List<ElectricityPriceData> priceDataList = new ArrayList<>();
        var formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        for (var i = 0; i < dataArray.size(); i++) {
            var data = dataArray
                    .get(i)
                    .getAsJsonObject();
            var timestamp = LocalDateTime.parse(data
                    .get("start_time")
                    .getAsString(), formatter);
            var price = data
                    .get("value")
                    .getAsDouble();
            priceDataList.add(new ElectricityPriceData(timestamp, price));
        }

        return priceDataList;
    }
}
