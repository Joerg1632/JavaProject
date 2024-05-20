package org.nsu.cars_storage_controller;

import org.nsu.storage.StoragesMap;
import org.nsu.threadpool.ThreadPool;

public class TasksController {
    protected StoragesMap storages;
    protected ThreadPool threadPool;

    public TasksController(int workersNum, StoragesMap storages) {
        this.storages = storages;
        this.threadPool = new ThreadPool(workersNum);
    }

    public void addTasks(int num) {
        for (int i = 0; i < num; i++) {
            threadPool.addTask(new WorkerTask(storages));
        }
    }
}
