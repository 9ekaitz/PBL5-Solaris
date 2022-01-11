package eus.solaris.solaris.controller.multithreading;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eus.solaris.solaris.controller.multithreading.modes.DayGroupMode;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.repository.DataEntryRepository;

public class ThreadController {

    InitialBuffer initialBuffer;
    GatherBuffer gatherBuffer;
    DataBuffer dataBuffer;

    Integer threads;

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

    public ThreadController(Integer threads, LocalDate startDate, LocalDate endDate, List<SolarPanel> solarPanels) {
        this.threads = threads;
        Integer maxCount = countDays(startDate, endDate);
        initialBuffer = new InitialBuffer(maxCount);
        gatherBuffer = new GatherBuffer(maxCount);
        dataBuffer = new DataBuffer();
        for (SolarPanel solarPanel : solarPanels) {
            insertDays(startDate, endDate, solarPanel);
        }

        for (int i = 0; i < threads; i++) {
            new Thread(new Gatherer(initialBuffer, gatherBuffer)).start();
        }

        for (int i = 0; i < threads; i++) {
            new Thread(new DayGrouper(gatherBuffer, dataBuffer, DayGroupMode.HOURLY)).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dataBuffer.print();
    }

}
