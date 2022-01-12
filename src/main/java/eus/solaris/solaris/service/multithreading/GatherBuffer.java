package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GatherBuffer {
    private Lock mutex;

    private List<Map<LocalDate, Map<Instant, Double>>> buffer;
    private Condition isEmpty, isFull;

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

    public void add(Map<LocalDate, Map<Instant, Double>> data) {
        this.mutex.lock();
        try {
            this.buffer.add(data);
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
            while (this.buffer.size() == 0) {
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
        buffer.forEach(action -> {
            action.forEach((key, value) -> {
                System.out.println(key);
                value.forEach((k, v) -> {
                    System.out.println(k + " " + v);
                });
            });
        });
    }

}
