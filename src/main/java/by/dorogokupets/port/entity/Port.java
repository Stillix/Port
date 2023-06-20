package by.dorogokupets.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    private static final int MAX_COUNT_PORT_CONTAINERS = 200;
    private int currentCountPortContainers;

    private static final int countPiers = 5;
    private ArrayDeque<Pier> piers;
    private static Lock lock = new ReentrantLock(true);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Port port;
    private Condition  pierAvailable = lock.newCondition();


    private Port() {
        piers = new ArrayDeque<>(countPiers);
        for (int i = 1; i <= countPiers; i++) {
            piers.add(new Pier(i));
        }
        currentCountPortContainers = MAX_COUNT_PORT_CONTAINERS;

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

    public Pier getPier() throws InterruptedException {
        lock.lock();
        try {
            while (piers.isEmpty()) {
                pierAvailable.await();
            }
            return piers.poll();
        } finally {
            lock.unlock();
        }
    }

    public void releasePier(Pier pier) {
        lock.lock();
        try {
            piers.add(pier);
            pierAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

    public void unloadContainers() {
        lock.lock();
        try {
            currentCountPortContainers++;
        } finally {
            lock.unlock();
        }
    }

    public void loadContainers() {
        lock.lock();
        try {
            currentCountPortContainers--;
        } finally {
            lock.unlock();
        }
    }

    public int getMaxCountPortContainers() {
        return MAX_COUNT_PORT_CONTAINERS;
    }

    public int getCurrentCountPortContainers() {
        return currentCountPortContainers;
    }
}
