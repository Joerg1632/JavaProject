package org.nsu.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    private final BlockingQueue<Worker> Workers;
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger taskQueueSize = new AtomicInteger(0);

    public ThreadPool(int threadPoolSize) {
        this.Workers = new ArrayBlockingQueue<>(threadPoolSize);

        for (int i = 0; i < threadPoolSize; i++) {
            Workers.offer(new Worker(taskQueue, taskQueueSize));
        }

        for (Worker thread : Workers) {
            new Thread(thread).start();
        }
    }

    public void addTask(Runnable task) {
        taskQueue.offer(task);
        taskQueueSize.incrementAndGet();
    }

    public void interrupt() {
        for (Worker thread : Workers) {
            thread.interrupt();
        }
    }

    public int getTaskQueueSize() {
        return taskQueueSize.get();
    }
}
