package eus.solaris.solaris.service.multithreading;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import eus.solaris.solaris.domain.SolarPanel;

public class InitialBuffer {
    private Lock mutex;

    private List<Map<LocalDate, List<SolarPanel>>> buffer;
    private Condition isEmpty;
    private Condition isFull;

    Integer maxCount;

    public InitialBuffer(Integer maxCount) {
        this.mutex = new ReentrantLock();
        this.buffer = new ArrayList<>();
        this.isEmpty = this.mutex.newCondition();
        this.isFull = this.mutex.newCondition();
        this.maxCount = maxCount;
    }

    public void add(LocalDate date, List<SolarPanel> panel) {
        this.mutex.lock();
        try {
            Map<LocalDate, List<SolarPanel>> map = new HashMap<>();
            map.put(date, panel);
            this.buffer.add(map);
            this.isEmpty.signal();
        } finally {
            this.mutex.unlock();
        }
    }

    public Map<LocalDate, List<SolarPanel>> get() {
        this.mutex.lock();
        try {
            if (maxCount == 0) {
                isEmpty.signal();
                return null;
            }
            while (this.buffer.isEmpty()) {
                this.isEmpty.await();
                if (maxCount == 0) {
                    return null;
                }
            }
            Map<LocalDate, List<SolarPanel>> data = this.buffer.get(0);
            this.buffer.remove(0);
            this.isFull.signal();
            this.maxCount--;
            return data;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            this.mutex.unlock();
        }
    }

    public Boolean isEmpty() {
        this.mutex.lock();
        try {
            return this.buffer.isEmpty();
        } finally {
            this.mutex.unlock();
        }
    }
}
