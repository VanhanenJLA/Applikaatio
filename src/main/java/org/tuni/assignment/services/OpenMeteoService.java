package org.tuni.assignment.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.tuni.assignment.models.WindSpeedData;
import org.tuni.assignment.utils.Klinu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class OpenMeteoService {
    private static final String API_URL = "https://archive-api.open-meteo.com/v1/archive";

    public List<WindSpeedData> fetchWindSpeeds(double latitude, double longitude, LocalDateTime startDate, LocalDateTime endDate) throws IOException, InterruptedException {
        var params = new HashMap<String, String>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("start_date", startDate.toLocalDate().toString());
        params.put("end_date", endDate.toLocalDate().toString());
        params.put("hourly", "windspeed_10m");

        var response = Klinu.sendGetRequest(API_URL, params, Optional.empty());

        // Parse JSON response
        var gson = new Gson();
        var jsonObject = gson.fromJson(response, JsonObject.class);

        // Extract the "hourly" JsonObject safely
        var hourlyObject = jsonObject.getAsJsonObject("hourly");

        // Make sure both "time" and "windspeed_10m" are JsonArrays
        var timeArray = hourlyObject.getAsJsonArray("time");
        var windSpeedArray = hourlyObject.getAsJsonArray("windspeed_10m");

        // Validate if timeArray and windSpeedArray are the same size
        if (timeArray.size() != windSpeedArray.size()) {
            throw new IllegalStateException("The number of timestamps and wind speeds don't match!");
        }

        List<WindSpeedData> windDataList = new ArrayList<>();
        var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        IntStream
                .range(0, timeArray.size())
                .forEach(i -> {
                    var timestamp = LocalDateTime.parse(timeArray
                            .get(i)
                            .getAsString(), formatter);
                    var windSpeed = windSpeedArray
                            .get(i)
                            .getAsDouble();
                    windDataList.add(new WindSpeedData(timestamp, windSpeed));
                });

        return windDataList;
    }

}