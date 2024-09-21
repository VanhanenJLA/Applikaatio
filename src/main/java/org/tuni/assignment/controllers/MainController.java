package org.tuni.assignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.util.StringConverter;
import org.tuni.assignment.models.CombinedData;
import org.tuni.assignment.models.Preferences;
import org.tuni.assignment.services.GeocodingService;
import org.tuni.assignment.services.SahkonHintaService;
import org.tuni.assignment.services.OpenMeteoService;
import org.tuni.assignment.utils.DataCombiner;
import org.tuni.assignment.utils.PreferencesManager;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainController {
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CheckBox priceCheckBox;

    @FXML
    private CheckBox windCheckBox;

    private Preferences preferences;
    private PreferencesManager preferencesManager;
    private SahkonHintaService priceService;
    private OpenMeteoService windService;
    private DataCombiner dataCombiner;

    @FXML
    public void initialize() {
        preferencesManager = PreferencesManager.getInstance();
        preferences = preferencesManager.getPreferences();
//        priceService = new FingridAPI();
        priceService = new SahkonHintaService();
        windService = new OpenMeteoService();
        dataCombiner = new DataCombiner();

        priceCheckBox.setSelected(preferences
                .getDisplayOptions()
                .isShowElectricityPrice());
        windCheckBox.setSelected(preferences
                .getDisplayOptions()
                .isShowWindSpeed());

        loadDataAndRenderGraph();
    }

    private void loadDataAndRenderGraph() {
        // Fetch data in background thread
        new Thread(() -> {
            try {
                // Convert LocalDate to LocalDateTime
                var startDateTime = preferences
                        .getStartDate()
                        .atStartOfDay();
                var endDateTime = preferences
                        .getEndDate()
                        .atStartOfDay();

                // Fetch data
//                var priceData = priceService.fetchElectricityPrices(startDateTime, endDateTime);
                var yesterday = LocalDate
                        .now()
                        .minusDays(1);
                var paiva = LocalDate.parse("2024-09-19");
                var priceData = priceService.fetchElectricityPrices(paiva);

                var geocodingService = new GeocodingService();
                var coordinates = geocodingService.getCoordinates(preferences.getCity());

                var windData = windService.fetchWindSpeeds(coordinates[0], coordinates[1], startDateTime, endDateTime);

                var combinedData = dataCombiner.combineData(priceData, windData);

                javafx.application.Platform.runLater(() -> renderGraph(combinedData));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void togglePrice() {
        preferences
                .getDisplayOptions()
                .setShowElectricityPrice(priceCheckBox.isSelected());
        preferencesManager.savePreferences(preferences);
        loadDataAndRenderGraph();
    }

    @FXML
    private void toggleWind() {
        preferences
                .getDisplayOptions()
                .setShowWindSpeed(windCheckBox.isSelected());
        preferencesManager.savePreferences(preferences);
        loadDataAndRenderGraph();
    }

    @FXML
    private void openSettings() {
        // TODO: Implement settings
        // Open SettingsWindow
    }

    @FXML
    private void exitApplication() {
        System.exit(0);
    }

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private void renderGraph(List<CombinedData> combinedData) {
        lineChart
                .getData()
                .clear();

        if (preferences
                .getDisplayOptions()
                .isShowElectricityPrice()) {
            var priceSeries = new XYChart.Series<String, Number>();
            priceSeries.setName("Electricity Price");

            combinedData.forEach(data -> {
                var dateTime = data
                        .getTimestamp()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                var x = dateTime.format(timeFormatter);
                priceSeries
                        .getData()
                        .add(new XYChart.Data<>(x, data.getPrice()));
            });
            lineChart
                    .getData()
                    .add(priceSeries);
        }

        if (preferences
                .getDisplayOptions()
                .isShowWindSpeed()) {
            var windSeries = new XYChart.Series<String, Number>();
            windSeries.setName("Wind Speed");

            combinedData.forEach(data -> {
                var dateTime = data
                        .getTimestamp()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                var x = dateTime.format(timeFormatter);
                windSeries
                        .getData()
                        .add(new XYChart.Data<>(x, data.getWindSpeed()));
            });
            lineChart
                    .getData()
                    .add(windSeries);
        }

//        // Adjust X-Axis to display dates
//        var xAxis = (NumberAxis) lineChart.getXAxis();
//        xAxis.setTickLabelFormatter(new StringConverter<>() {
//            @Override
//            public String toString(Number object) {
//                var instant = Instant.ofEpochMilli(object.longValue());
//                var time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//                return time.format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
//            }
//
//            @Override
//            public Number fromString(String string) {
//                return null;
//            }
//        });
    }


}
