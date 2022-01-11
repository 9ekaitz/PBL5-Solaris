package eus.solaris.solaris.controller.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import eus.solaris.solaris.controller.multithreading.modes.DayGroupMode;

public class DayGrouper implements Runnable {

    GatherBuffer gatherBuffer;
    DataBuffer dataBuffer;
    DayGroupMode mode;

    public DayGrouper(GatherBuffer gatherBuffer, DataBuffer dataBuffer, DayGroupMode mode) {
        this.gatherBuffer = gatherBuffer;
        this.dataBuffer = dataBuffer;
        this.mode = mode;
    }

    private Double getKWh(List<Double> dataList) {
        Double kWmin = 0.0;

        for (Double power : dataList) {
            kWmin += power;
        }

        Double kWh = kWmin / 60;

        return kWh;
    }

    private void groupAndInsert(Map<LocalDate, Map<Instant, Double>> data) {
        Entry<LocalDate, Map<Instant, Double>> entry = data.entrySet().iterator().next();
        LocalDate day = entry.getKey();
        Map<Instant, Double> dataMap = entry.getValue();

        System.out.println("[DEBUG] Grouping data for " + day + " mode: " + mode);

        if (mode == DayGroupMode.HOURLY) {
            Map<Instant, List<Double>> groupedData = new HashMap<>();
            for (Entry<Instant, Double> dataEntry : dataMap.entrySet()) {
                Instant instant = dataEntry.getKey();
                Double power = dataEntry.getValue();
                Instant hour = instant.truncatedTo(ChronoUnit.HOURS);

                if (groupedData.containsKey(hour)) {
                    groupedData.get(hour).add(power);
                } else {
                    List<Double> newList = new ArrayList<>();
                    newList.add(power);
                    groupedData.put(hour, newList);
                }
            }

            for (Entry<Instant, List<Double>> groupedDataEntry : groupedData.entrySet()) {
                Instant hour = groupedDataEntry.getKey();
                List<Double> powerList = groupedDataEntry.getValue();
                Double kWh = getKWh(powerList);

                Map<Instant, Double> newData = new HashMap<>();
                newData.put(hour, kWh);

                dataBuffer.put(newData);
            }

        } else {
            List<Double> powerList = new ArrayList<>();
            for (Entry<Instant, Double> dataEntry : dataMap.entrySet()) {
                powerList.add(dataEntry.getValue());
            }
            Double kWh = getKWh(powerList);

            Map<Instant, Double> newData = new HashMap<>();
            newData.put(day.atStartOfDay().toInstant(ZoneOffset.UTC), kWh);

            dataBuffer.put(newData);
        }

    }

    @Override
    public void run() {
        while (true) {
            Map<LocalDate, Map<Instant, Double>> data = gatherBuffer.get();

            if (data == null) {
                System.out
                        .println("[DEBUG] DayGrouper thread " + Thread.currentThread().getName() + " terminated: EOL.");
                break;
            }
            groupAndInsert(data);
        }
    }

}
