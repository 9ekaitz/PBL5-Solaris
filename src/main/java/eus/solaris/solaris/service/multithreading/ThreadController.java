package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.modes.GroupMode;

public class ThreadController implements ICompletionObserver {

    InitialBuffer initialBuffer;
    GatherBuffer gatherBuffer;
    DataBuffer dataBuffer;

    LocalDate startDate;
    LocalDate endDate;

    Boolean doContinue;

    Object sync;

    Integer threads;

    public ThreadController(Integer threads, LocalDate startDate, LocalDate endDate) {
        this.threads = threads;
        Integer maxCount = countDays(startDate, endDate);
        initialBuffer = new InitialBuffer(maxCount);
        gatherBuffer = new GatherBuffer(maxCount, this);
        dataBuffer = new DataBuffer();
        this.startDate = startDate;
        this.endDate = endDate;
        this.sync = new Object();
        doContinue = false;
    }

    private void insertDays(LocalDate startDate, LocalDate endDate, List<SolarPanel> solarPanels) {
        LocalDate currentDate = startDate;

        if (startDate.isEqual(endDate)) {
            initialBuffer.add(startDate, solarPanels);
        }

        while (currentDate.isBefore(endDate)) {
            initialBuffer.add(currentDate, solarPanels);
            currentDate = currentDate.plusDays(1);
        }
    }

    private Integer countDays(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        Integer count = 0;
        while (currentDate.isBefore(endDate)) {
            count++;
            currentDate = currentDate.plusDays(1);
        }

        if (startDate.isEqual(endDate)) {
            count = 1;
        }

        return count;
    }

    public Map<Instant, Double> prepareData(List<SolarPanel> solarPanels, GroupMode groupMode,
            ConversionType conversionType) {
        insertDays(startDate, endDate, solarPanels);

        for (int i = 0; i < threads / 2; i++) {
            new Thread(new Gatherer(initialBuffer, gatherBuffer)).start();
        }

        for (int i = 0; i < threads / 2; i++) {
            new Thread(new Grouper(gatherBuffer, dataBuffer, groupMode)).start();
        }

        synchronized (sync) {
            try {
                while (!doContinue) {
                    sync.wait();
                }
            } catch (InterruptedException e) {
                System.exit(-1);
            }
        }

        Map<Instant, Double> dataMap = dataBuffer.getData();

        if (solarPanels.size() > 1) {
            dataMap = Processer.groupPanels(dataMap);
        }

        if (groupMode == GroupMode.YEAR) {
            dataMap = Grouper.groupByMonth(dataMap);
        }
        return Processer.process(dataMap, conversionType);
    }

    @Override
    public void complete() {
        synchronized (sync) {
            doContinue = true;
            sync.notifyAll();
        }
    }

}
