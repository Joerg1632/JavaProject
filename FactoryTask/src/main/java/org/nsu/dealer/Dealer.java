package org.nsu.dealer;

import org.nsu.details.Car;
import org.nsu.cars_storage_controller.CarsStorageController;
import org.nsu.SingletonLogger;
import org.nsu.util.IdsGenerator;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dealer extends Thread {
    protected long id;
    protected int period;
    protected final CarsStorageController carsStorageController;

    private static final SingletonLogger logger = new SingletonLogger("DealerLog", "log.txt");

    private final boolean log;

    public Dealer(int period, CarsStorageController carsStorageController, boolean log) {
        this.id = IdsGenerator.generateId();
        this.carsStorageController = carsStorageController;
        this.period = period;
        this.log = log;
    }

    public Dealer(int period, CarsStorageController carsStorageController) {
        this(period, carsStorageController, false);
    }

    public int getSpeed() {
        return this.period;
    }

    public void run() {
        while (true) {
            try {
                Car car = orderCar();
                if (log) {
                    String msg = MessageFormat.format("Dealer {0} : Auto {1} : (Body: {2}, Engine {3}, Accessory {4})",
                            id, car.getId(), car.getBody().getId(), car.getEngine().getId(), car.getAccessory().getId());
                    logger.getLogger().log(Level.INFO, msg);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Car orderCar() throws InterruptedException {
        sleep(period);
        synchronized (carsStorageController) {
            carsStorageController.notify();
        }
        return (Car) carsStorageController.getCarStorage().get();
    }

    public void setSpeed(int period) {
        this.period = period;
    }

}