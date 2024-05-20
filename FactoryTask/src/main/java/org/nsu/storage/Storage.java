package org.nsu.storage;

import java.util.LinkedList;
import java.util.Objects;

public class Storage<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;
    private int totalProduced = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized int getCurrentSize() {
        return queue.size();
    }

    public synchronized boolean isFull() {
        return queue.size() == capacity;
    }

    public synchronized int getTotalProduced() {
        return totalProduced;
    }

    public synchronized T get() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        T item = queue.removeFirst();
        notifyAll();
        return item;
    }

    public synchronized void put(T el) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.addLast(el);
        totalProduced++;
        notifyAll();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Storage<?> storage = (Storage<?>) object;
        return capacity == storage.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }
}
