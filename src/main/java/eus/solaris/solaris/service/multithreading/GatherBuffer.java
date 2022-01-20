package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GatherBuffer {
    private Lock mutex;

    private List<Map<LocalDate, Map<Instant, Double>>> buffer;
    private Condition isEmpty;
    private Condition isFull;

    ICompletionObserver observer;

    Integer maxCount;

    public GatherBuffer(Integer maxCount, ICompletionObserver observer) {
        this.mutex = new ReentrantLock();
        this.buffer = new ArrayList<>();
        this.isEmpty = this.mutex.newCondition();
        this.isFull = this.mutex.newCondition();
        this.maxCount = maxCount;
        this.observer = observer;
    }

    public void add(Map<LocalDate, Map<Instant, Double>> dataMap) {
        this.mutex.lock();
        try {
            this.buffer.add(dataMap);
            this.isEmpty.signal();
        } finally {
            this.mutex.unlock();
        }
    }

    public Map<LocalDate, Map<Instant, Double>> get() {
        this.mutex.lock();
        try {
            if (maxCount == 0) {
                observer.complete();
                isEmpty.signal();
                return null;
            }
            while (this.buffer.isEmpty()) {
                this.isEmpty.await();
                if (maxCount == 0) {
                    observer.complete();
                    return null;
                }
            }
            Map<LocalDate, Map<Instant, Double>> data = this.buffer.get(0);
            this.buffer.remove(0);
            this.isFull.signal();
            this.maxCount--;
            return data;
        } catch (InterruptedException e) {
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
