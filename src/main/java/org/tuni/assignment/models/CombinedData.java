package org.tuni.assignment.models;

import java.time.LocalDateTime;

public class CombinedData {
    private final LocalDateTime timestamp;
    private final double price;
    private final double windSpeed;

    public CombinedData(LocalDateTime timestamp, double price, double windSpeed) {
        this.timestamp = timestamp;
        this.price = price;
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
