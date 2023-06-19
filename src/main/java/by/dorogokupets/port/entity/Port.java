package by.dorogokupets.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    private int maxCountPortContainers = 120;
    private int currentCountPortContainers;
    private int currentShipContainers;

    private static final int countPiers = 4;
    private ArrayDeque<Pier> piers;
    private static Lock lock = new ReentrantLock(true);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Port port;

    private Port() {
        piers = new ArrayDeque<>(countPiers);
        for (int i = 1; i <= countPiers; i++) {
            piers.add(new Pier(i));
        }
        currentCountPortContainers = maxCountPortContainers;
    }

    public static Port getInstanceUsingDoubleLocking() {
        if (port == null) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    port = new Port();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return port;
    }

    public Pier getPier() {
        Pier pier = null;
        lock.lock();
        try {
            pier = piers.poll();
        } finally {
            lock.unlock();
        }
        return pier;
    }

    public void releasePier(Pier pier) {
        lock.lock();
        try {
            piers.add(pier);
        } finally {
            lock.unlock();
        }
    }

    public void unloadContainers() {
        lock.lock();
        try {
            currentShipContainers--;
            currentCountPortContainers++;
        } finally {
            lock.unlock();
        }
    }

    public void loadContainers() {
        lock.lock();
        try {
            currentShipContainers++;
            currentCountPortContainers--;
        } finally {
            lock.unlock();
        }
    }

    public int getMaxCountPortContainers() {
        return maxCountPortContainers;
    }

    public int getCurrentCountPortContainers() {
        return currentCountPortContainers;
    }

    public int getCurrentShipContainers() {
        return currentShipContainers;
    }
}
