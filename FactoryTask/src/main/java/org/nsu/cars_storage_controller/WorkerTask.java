package org.nsu.cars_storage_controller;

import org.nsu.details.*;
import org.nsu.storage.*;

import static java.lang.Thread.sleep;

public class WorkerTask implements Runnable {

    protected final StoragesMap storages;

    public WorkerTask(StoragesMap storages) {
        this.storages = storages;
    }

    @Override
    public void run() {
        Body body;
        Engine engine;
        Accessory accessory;

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        body = (Body) storages.get(StoragesMap.StoragesNames.bodies).get();
        engine = (Engine) storages.get(StoragesMap.StoragesNames.engines).get();
        accessory = (Accessory)  storages.get(StoragesMap.StoragesNames.accessories).get();

        Car car = new Car(body, engine, accessory);

        storages.get(StoragesMap.StoragesNames.cars).put(car);
    }
}
