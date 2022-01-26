package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import eus.solaris.solaris.service.multithreading.modes.GroupMode;

public class Grouper implements Runnable {

    private GatherBuffer gatherBuffer;
    private DataBuffer dataBuffer;
    private GroupMode mode;

    public Grouper(GatherBuffer gatherBuffer, DataBuffer dataBuffer, GroupMode mode) {
        this.gatherBuffer = gatherBuffer;
        this.dataBuffer = dataBuffer;
        this.mode = mode;
    }

    public static Double getKWhSumFromKWminList(List<Double> dataList) {
        Double kWmin = 0.0;

        for (Double power : dataList) {
            kWmin += power;
        }

        return kWmin / 60;
    }

    public static Map<Instant, Double> groupByMonth(Map<Instant, Double> dataMap) {
        Map<Instant, Double> groupedMap = new HashMap<>();

        for (Entry<Instant, Double> entry : dataMap.entrySet()) {
            Instant instant = entry.getKey();
            Double value = entry.getValue();

            LocalDate month = LocalDate.ofInstant(instant, ZoneOffset.UTC).withDayOfMonth(1);

            Instant monthInstant = month.atStartOfDay().toInstant(ZoneOffset.UTC);

            if (groupedMap.containsKey(monthInstant)) {
                groupedMap.put(monthInstant, groupedMap.get(monthInstant) + value);
            } else {
                groupedMap.put(monthInstant, value);
            }
        }

        return groupedMap;
    }

    private void groupWeek(Map<Instant, Double> dataMap) {
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
            Double kWh = Grouper.getKWhSumFromKWminList(powerList);
            dataBuffer.put(hour, kWh);
        }
    }

    private void groupDay(Map<Instant, Double> dataMap) {
        for (Entry<Instant, Double> dataEntry : dataMap.entrySet()) {
            dataBuffer.put(dataEntry.getKey(), dataEntry.getValue());
        }
    }

    private void groupDefault(Map<Instant, Double> dataMap, LocalDate day) {
        List<Double> powerList = new ArrayList<>();
        for (Entry<Instant, Double> dataEntry : dataMap.entrySet()) {
            powerList.add(dataEntry.getValue());
        }
        Double kWh = Grouper.getKWhSumFromKWminList(powerList);
        dataBuffer.put(day.atStartOfDay().toInstant(ZoneOffset.UTC), kWh);
    }

    private void groupAndInsert(Map<LocalDate, Map<Instant, Double>> data) {
        Entry<LocalDate, Map<Instant, Double>> entry = data.entrySet().iterator().next();
        LocalDate day = entry.getKey();
        Map<Instant, Double> dataMap = entry.getValue();

        if (mode == GroupMode.WEEK) {
            groupWeek(dataMap);
        } else if (mode == GroupMode.DAY) {
            groupDay(dataMap);
        } else {
            groupDefault(dataMap, day);
        }

    }

    @Override
    public void run() {
        while (true) {
            Map<LocalDate, Map<Instant, Double>> data = gatherBuffer.get();

            if (data == null) {
                break;
            }
            groupAndInsert(data);
        }
    }

}
