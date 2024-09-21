package org.tuni.assignment.utils;

import org.tuni.assignment.models.CombinedData;
import org.tuni.assignment.models.ElectricityPriceData;
import org.tuni.assignment.models.WindSpeedData;

import java.time.LocalDateTime;
import java.util.*;

public class DataCombiner {
    public List<CombinedData> combineData(List<ElectricityPriceData> priceDataList, List<WindSpeedData> windDataList) {
        Map<LocalDateTime, Double> priceMap = new HashMap<>();
        for (var priceData : priceDataList) {
            priceMap.put(priceData.getTimestamp(), priceData.getPrice());
        }

        Map<LocalDateTime, Double> windMap = new HashMap<>();
        for (var windData : windDataList) {
            windMap.put(windData.getTimestamp(), windData.getWindSpeed());
        }

        Set<LocalDateTime> allTimestamps = new HashSet<>();
        allTimestamps.addAll(priceMap.keySet());
        allTimestamps.addAll(windMap.keySet());

        List<CombinedData> combinedDataList = new ArrayList<>();
        for (var timestamp : allTimestamps) {
            var price = priceMap.getOrDefault(timestamp, null);
            var windSpeed = windMap.getOrDefault(timestamp, null);
            if (price != null && windSpeed != null) {
                combinedDataList.add(new CombinedData(timestamp, price, windSpeed));
            }
        }

        // Sort the list by timestamp
        combinedDataList.sort(Comparator.comparing(CombinedData::getTimestamp));

        return combinedDataList;
    }
}
