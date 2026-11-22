package data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings("ALL")
public class WithLockTestData {
    private final Lock lock = new ReentrantLock();
    private final Lock otherLock = new ReentrantLock();
    private final ReadWriteLock stockLock = new ReentrantReadWriteLock();
    private int balance;

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public int balance() {
        this.lock.lock();
        try {
            return balance;
        } finally {
            this.lock.unlock();
        }
    }

    public int readStock() {
        stockLock.readLock().lock();
        try {
            int current = balance;
            return current * 2;
        } finally {
            stockLock.readLock().unlock();
        }
    }

    public void withCatch() {
        lock.lock();
        try {
            balance++;
        } catch (RuntimeException e) {
            balance--;
        } finally {
            lock.unlock();
        }
    }

    public void differentLock() {
        lock.lock();
        try {
            balance++;
        } finally {
            otherLock.unlock();
        }
    }

    public void moreInFinally() {
        lock.lock();
        try {
            balance++;
        } finally {
            lock.unlock();
            balance--;
        }
    }

    public void statementBetween() {
        lock.lock();
        balance++;
        try {
            balance++;
        } finally {
            lock.unlock();
        }
    }

    public void tryLock() {
        if (lock.tryLock()) {
            try {
                balance++;
            } finally {
                lock.unlock();
            }
        }
    }

    public void lockInsideTry() {
        try {
            lock.lock();
            balance++;
        } finally {
            lock.unlock();
        }
    }

    public void freshLockEachTime() {
        newLock().lock();
        try {
            balance++;
        } finally {
            newLock().unlock();
        }
    }

    private Lock newLock() {
        return new ReentrantLock();
    }
}
