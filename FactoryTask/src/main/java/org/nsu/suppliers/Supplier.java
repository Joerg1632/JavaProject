package org.nsu.suppliers;

import org.nsu.details.Detail;
import org.nsu.storage.Storage;

abstract public class Supplier extends Thread {

    protected int period;
    protected final Storage<Detail> storage;

    public Supplier(int period, Storage<Detail> storage) {
        this.period = period;
        this.storage = storage;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int newPeriod) {
        if (newPeriod > 0) {
            this.period = newPeriod;
        }
    }

    public void run() {
        while (true) {
            try {
                deliver();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    abstract public void deliver() throws InterruptedException;
}
