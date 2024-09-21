package org.tuni.assignment.models;

import java.time.LocalDateTime;

public class ElectricityPriceData {
    private final LocalDateTime timestamp;
    private final double price;

    public ElectricityPriceData(LocalDateTime timestamp, double price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }
}
