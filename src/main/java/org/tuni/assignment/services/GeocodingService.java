package org.tuni.assignment.services;

import com.google.gson.JsonParser;
import org.tuni.assignment.utils.Klinu;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class GeocodingService {
    private static final String API_URL = "https://nominatim.openstreetmap.org/search";

    public double[] getCoordinates(String city) throws IOException, InterruptedException {
        var url = API_URL + "?format=json&q=" + URLEncoder.encode(city, StandardCharsets.UTF_8);

        var response = Klinu.sendGetRequest(url, null, Optional.empty());

        var jsonArray = JsonParser
                .parseString(response)
                .getAsJsonArray();
        if (jsonArray.isEmpty()) {
            throw new IOException("City not found");
        }
        var location = jsonArray
                .get(0)
                .getAsJsonObject();
        var latitude = location
                .get("lat")
                .getAsDouble();
        var longitude = location
                .get("lon")
                .getAsDouble();
        return new double[]{latitude, longitude};
    }
}
