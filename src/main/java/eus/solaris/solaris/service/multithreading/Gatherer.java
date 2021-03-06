package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.util.SpringContextUtil;

public class Gatherer implements Runnable {

    private DataEntryRepository dataEntryRepository;

    private InitialBuffer initialBuffer;
    private GatherBuffer gatherBuffer;

    public Gatherer(InitialBuffer initialBuffer, GatherBuffer gatherBuffer) {
        this.initialBuffer = initialBuffer;
        this.gatherBuffer = gatherBuffer;
        this.dataEntryRepository = SpringContextUtil.getBean(DataEntryRepository.class);
    }

    public static Map<Instant, Double> extractData(List<SolarPanelDataEntry> dataEntries) {
        Map<Instant, Double> dataMap = new TreeMap<>();
        for (SolarPanelDataEntry solarPanelDataEntry : dataEntries) {
            if (dataMap.containsKey(solarPanelDataEntry.getTimestamp())) {
                dataMap.put(solarPanelDataEntry.getTimestamp(),
                        dataMap.get(solarPanelDataEntry.getTimestamp()) + solarPanelDataEntry.getPower());
            } else {
                dataMap.put(solarPanelDataEntry.getTimestamp(), solarPanelDataEntry.getPower());
            }
        }
        return dataMap;
    }

    private Map<LocalDate, Map<Instant, Double>> processData(List<SolarPanelDataEntry> dataEntries, LocalDate day) {
        Map<LocalDate, Map<Instant, Double>> returnMap = new HashMap<>();
        returnMap.put(day, Gatherer.extractData(dataEntries));
        return returnMap;
    }

    @Override
    public void run() {
        while (true) {
            Map<LocalDate, List<SolarPanel>> map = initialBuffer.get();
            if (map == null) {
                System.out.println("[DEBUG] Gather thread " + Thread.currentThread().getName() + " terminated: EOL.");
                break;
            }

            Entry<LocalDate, List<SolarPanel>> entry = map.entrySet().iterator().next();
            LocalDate day = entry.getKey();
            List<SolarPanel> solarPanels = entry.getValue();

            System.out.println("[DEBUG] Gathering data for " + day + ".");

            List<SolarPanelDataEntry> dataEntries = dataEntryRepository.findBySolarPanelsAndLocalDate(solarPanels, day);

            Map<LocalDate, Map<Instant, Double>> dataMap = processData(dataEntries, day);

            System.out.println("[DEBUG] Gathered data for " + day + ".");

            gatherBuffer.add(dataMap);
        }
    }
}
