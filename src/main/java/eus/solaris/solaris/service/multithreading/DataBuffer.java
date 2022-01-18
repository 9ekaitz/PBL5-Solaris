package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataBuffer {
    private Lock mutex;

    private List<Entry<Instant, Double>> buffer;

    private Condition isEmpty, isFull;

    public DataBuffer() {
        this.mutex = new ReentrantLock();
        this.buffer = new ArrayList<>();
        this.isEmpty = this.mutex.newCondition();
        this.isFull = this.mutex.newCondition();
    }

    public void put(Instant instant, Double value) {
        this.mutex.lock();
        try {
            Entry<Instant, Double> entry = new SimpleEntry<>(instant, value);
            this.buffer.add(entry);
            this.isEmpty.signal();
        } finally {
            this.mutex.unlock();
        }
    }

    public Entry<Instant, Double> get() {
        this.mutex.lock();
        try {
            while (this.buffer.size() == 0) {
                this.isEmpty.await();
            }
            Entry<Instant, Double> data = this.buffer.get(0);
            this.buffer.remove(0);
            this.isFull.signal();
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

    public Map<Instant, Double> getData() {
        this.mutex.lock();
        Map<Instant, Double> data = new TreeMap<>();
        for (Entry<Instant, Double> entry : buffer) {
            Instant instant = entry.getKey();
            Double value = entry.getValue();
            data.put(instant, value);
        }
        this.mutex.unlock();
        return data;
    }
}