package org.nsu.cars_storage_controller;

import org.nsu.storage.*;
import org.nsu.threadpool.BlockingQueue;
import org.nsu.threadpool.ThreadPool;

public class TasksController {

    protected StoragesMap storages;
    protected BlockingQueue<Runnable> tasks;

    protected ThreadPool workers;

    public TasksController(int workersNum, StoragesMap storages) {
        this.storages = storages;
        this.tasks = new BlockingQueue<>();
        this.workers = new ThreadPool(workersNum, tasks);
        this.workers.start();
    }

    public void addTasks(int num) {
        for (int i = 0; i < num; i++) {
            tasks.put(new WorkerTask(storages));
        }
    }

}
