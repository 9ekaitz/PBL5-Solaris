package eus.solaris.solaris.controller.multithreading;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataBuffer {
    private Lock mutex;

    private List<Map<Instant, Double>> buffer;

    private Condition isEmpty, isFull;

    public DataBuffer() {
        this.mutex = new ReentrantLock();
        this.buffer = new ArrayList<>();
        this.isEmpty = this.mutex.newCondition();
        this.isFull = this.mutex.newCondition();
    }

    public void put(Map<Instant, Double> data) {
        this.mutex.lock();
        try {
            this.buffer.add(data);
            this.isEmpty.signal();
        } finally {
            this.mutex.unlock();
        }
    }

    public Map<Instant, Double> get() {
        this.mutex.lock();
        try {
            while (this.buffer.size() == 0) {
                this.isEmpty.await();
            }
            Map<Instant, Double> data = this.buffer.get(0);
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

    public void print() {
        buffer.forEach(System.out::println);
    }
}
