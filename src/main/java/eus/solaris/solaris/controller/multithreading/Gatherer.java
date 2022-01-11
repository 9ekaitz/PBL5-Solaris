package eus.solaris.solaris.controller.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    private Map<LocalDate, Map<Instant, Double>> processData(List<SolarPanelDataEntry> dataEntries, LocalDate day) {
        Map<LocalDate, Map<Instant, Double>> returnMap = new HashMap<>();
        Map<Instant, Double> dataMap = new HashMap<>();
        for (SolarPanelDataEntry solarPanelDataEntry : dataEntries) {
            dataMap.put(solarPanelDataEntry.getTimestamp(), solarPanelDataEntry.getPower());
        }
        returnMap.put(day, dataMap);
        return returnMap;
    }

    @Override
    public void run() {
        while (true) {
            Map<LocalDate, SolarPanel> map = initialBuffer.get();
            if (map == null) {
                System.out.println("[DEBUG] Gather thread " + Thread.currentThread().getName() + " terminated: EOL.");
                break;
            }

            Entry<LocalDate, SolarPanel> entry = map.entrySet().iterator().next();
            LocalDate day = entry.getKey();
            SolarPanel solarPanel = entry.getValue();

            System.out.println("[DEBUG] Gathering data for " + day + " for " + solarPanel.getId());

            List<SolarPanelDataEntry> dataEntries = dataEntryRepository.findBySolarPanelAndLocalDate(solarPanel, day);

            Map<LocalDate, Map<Instant, Double>> dataMap = processData(dataEntries, day);

            System.out.println("[DEBUG] Gathered data for " + day + " for " + solarPanel.getId());

            gatherBuffer.add(dataMap);
        }
    }
}
