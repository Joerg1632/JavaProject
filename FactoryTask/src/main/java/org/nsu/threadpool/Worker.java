package org.nsu.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicInteger taskQueueSize;

    public Worker(BlockingQueue<Runnable> taskQueue, AtomicInteger taskQueueSize) {
        this.taskQueue = taskQueue;
        this.taskQueueSize = taskQueueSize;
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        while (true) {
            try {
                taskQueue.take().run();
                taskQueueSize.decrementAndGet();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
