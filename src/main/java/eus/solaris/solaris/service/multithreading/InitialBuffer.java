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

    private List<Map<LocalDate, SolarPanel>> buffer;
    private Condition isEmpty, isFull;

    Integer maxCount;

    public InitialBuffer(Integer maxCount) {
        this.mutex = new ReentrantLock();
        this.buffer = new ArrayList<>();
        this.isEmpty = this.mutex.newCondition();
        this.isFull = this.mutex.newCondition();
        this.maxCount = maxCount;
    }

    public void add(LocalDate date, SolarPanel panel) {
        this.mutex.lock();
        try {
            while (this.buffer.size() == 10) {
                this.isFull.await();
            }
            Map<LocalDate, SolarPanel> map = new HashMap<>();
            map.put(date, panel);
            this.buffer.add(map);
            this.isEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.mutex.unlock();
        }
    }

    public Map<LocalDate, SolarPanel> get() {
        this.mutex.lock();
        try {
            if (maxCount == 0) {
                isEmpty.signal();
                return null;
            }
            while (this.buffer.size() == 0) {
                this.isEmpty.await();
                if (maxCount == 0) {
                    return null;
                }
            }
            Map<LocalDate, SolarPanel> data = this.buffer.get(0);
            this.buffer.remove(0);
            this.isFull.signal();
            this.maxCount--;
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.mutex.unlock();
        }
        return null;
    }

    public Boolean isEmpty() {
        this.mutex.lock();
        try {
            return this.buffer.size() == 0;
        } finally {
            this.mutex.unlock();
        }
    }
}
