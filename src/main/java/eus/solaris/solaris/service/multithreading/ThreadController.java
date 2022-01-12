package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.modes.DayGroupMode;

public class ThreadController implements ICompletionObserver {

    InitialBuffer initialBuffer;
    GatherBuffer gatherBuffer;
    DataBuffer dataBuffer;

    LocalDate startDate;
    LocalDate endDate;

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
    }

    private void insertDays(LocalDate startDate, LocalDate endDate, SolarPanel solarPanel) {
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            initialBuffer.add(currentDate, solarPanel);
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
        return count;
    }

    public Map<Instant, Double> prepareData(List<SolarPanel> solarPanels, DayGroupMode dayGroupMode,
            ConversionType conversionType) {
        for (SolarPanel solarPanel : solarPanels) {
            insertDays(startDate, endDate, solarPanel);
        }

        for (int i = 0; i < threads; i++) {
            new Thread(new Gatherer(initialBuffer, gatherBuffer)).start();
        }

        for (int i = 0; i < threads; i++) {
            new Thread(new DayGrouper(gatherBuffer, dataBuffer, dayGroupMode)).start();
        }

        synchronized (sync) {
            try {
                sync.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Map<Instant, Double> data = Processer.process(dataBuffer, conversionType);
        return data;
    }

    @Override
    public void complete() {
        synchronized (sync) {
            sync.notify();
        }
    }

}
