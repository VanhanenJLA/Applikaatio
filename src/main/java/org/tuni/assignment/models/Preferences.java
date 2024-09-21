package org.tuni.assignment.models;

import java.time.LocalDate;

public class Preferences {
    private final DisplayOptions displayOptions;
    private LocalDate startDate;
    private LocalDate endDate;
    private String city;

    public Preferences() {
        // Set default preferences
        this.startDate = LocalDate
                .now()
                .minusDays(7);
        this.endDate = LocalDate.now();
        this.city = "Helsinki";
        this.displayOptions = new DisplayOptions(true, true);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DisplayOptions getDisplayOptions() {
        return displayOptions;
    }
}
