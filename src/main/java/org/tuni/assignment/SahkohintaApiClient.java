package org.tuni.assignment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SahkohintaApiClient {
    public static void main(String[] args) {
        // Define query parameters
        String tulos = "sarja";
        String aikaraja = "2022-11-20";
        String tunnit = "24";

        // Construct the full URL with query parameters
        String apiUrl = String.format(
            "https://www.sahkohinta-api.fi/api/v1/halpa?tunnit=%s&tulos=%s&aikaraja=%s",
            tunnit, tulos, aikaraja
        );

        // Create an HttpClient with default settings
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Build the HTTP GET request with necessary headers
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                // Mandatory headers
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/128.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9," +
                        "image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Accept-Language", "en,en-FI;q=0.9,fi;q=0.8")
                .header("Cache-Control", "max-age=0")
//                .header("Connection", "keep-alive")
//                .header("Host", "www.sahkohinta-api.fi")
                .header("Referer", "https://statics.teams.cdn.office.net/")
                .header("Upgrade-Insecure-Requests", "1")
                // Client hints (optional)
                .header("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", " +
                        "\"Google Chrome\";v=\"128\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                // Optionally, add other headers if necessary
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Output response details
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Headers:");
            response.headers().map().forEach((key, values) -> {
                System.out.println(key + ": " + String.join(", ", values));
            });
            System.out.println("\nResponse Body:\n" + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
