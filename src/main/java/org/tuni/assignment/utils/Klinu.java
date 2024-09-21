package org.tuni.assignment.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class Klinu {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String sendGetRequest(String url, Map<String, String> parameters, Optional<String> apiKey) throws IOException, InterruptedException {
        StringBuilder fullUrl = new StringBuilder(url);
        if (parameters != null
                && !parameters.isEmpty()) {
            fullUrl.append("?");
            parameters.forEach((k, v) -> {
                try {
                    fullUrl
                            .append(URLEncoder.encode(k, StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(v, StandardCharsets.UTF_8))
                            .append("&");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            fullUrl.deleteCharAt(fullUrl.length() - 1); // Remove trailing '&'
        }

        var builder = HttpRequest
                .newBuilder()
                .uri(URI.create(fullUrl.toString()))
                // SähkönHinta API vastaa 403 ilman User-Agent headeria.
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/128.0.0.0 Safari/537.36")
                .GET();

        // Tää on turha muihin, kuin Fingridiin.
        apiKey.ifPresent(s -> builder.header("x-api-key", s));
        var request = builder.build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String sendGetRequest(String apiUrl, Map<String, String> params) throws IOException, InterruptedException {
        return sendGetRequest(apiUrl, params, Optional.empty());
    }
}