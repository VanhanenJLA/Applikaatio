package org.tuni.assignment.models;

import java.time.LocalDateTime;

public class WindSpeedData {
    private final LocalDateTime timestamp;
    private final double windSpeed;

    public WindSpeedData(LocalDateTime timestamp, double windSpeed) {
        this.timestamp = timestamp;
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
