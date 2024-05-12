package org.nsu;

import org.nsu.cars_storage_controller.CarsStorageController;
import org.nsu.cars_storage_controller.TasksController;
import org.nsu.config.ConfigReader;
import org.nsu.dealer.Dealer;
import org.nsu.suppliers.AccessoriesSupplier;
import org.nsu.suppliers.BodiesSupplier;
import org.nsu.suppliers.EnginesSupplier;
import org.nsu.storage.*;
import org.nsu.service.Observer;
import org.nsu.gui.events.*;
import org.nsu.gui.FactoryPanel;
import org.nsu.gui.events.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame implements Runnable, Observer {

    public final ArrayList<Dealer> dealers;
    public final Settings settings;
    public final ArrayList<AccessoriesSupplier> accessoriesSuppliers;
    public final BodiesSupplier bodiesSupplier;
    public final EnginesSupplier enginesSupplier;

    public final StoragesMap storages;
    public final CarsStorageController carsStorageController;

    public Main(ConfigReader configReader, Settings settings) {
        this.settings = settings;
        CarsStorage carsStorage = new CarsStorage(configReader.get(ConfigReader.Settings.storageAutoSize));
        BodiesStorage bodiesStorage = new BodiesStorage(configReader.get(ConfigReader.Settings.storageBodySize));
        EnginesStorage enginesStorage = new EnginesStorage(configReader.get(ConfigReader.Settings.storageEngineSize));
        AccessoriesStorage accessoriesStorage = new AccessoriesStorage(configReader.get(ConfigReader.Settings.storageAccessorySize));

        accessoriesSuppliers = new ArrayList<>();
        for (int i = 0; i < configReader.get(ConfigReader.Settings.accessorySuppliers); i++) {
            accessoriesSuppliers.add(new AccessoriesSupplier(settings.accessoriesSupplierSpeed, accessoriesStorage));
        }

        bodiesSupplier = new BodiesSupplier(settings.bodiesSupplierSpeed, bodiesStorage);
        enginesSupplier = new EnginesSupplier(settings.enginesSupplierSpeed, enginesStorage);

        storages = new StoragesMap(enginesStorage, bodiesStorage, accessoriesStorage, carsStorage);

        TasksController tasksController = new TasksController(configReader.get(ConfigReader.Settings.workers), storages);
        carsStorageController = new CarsStorageController(carsStorage, tasksController);

        dealers = new ArrayList<>();
        boolean log = configReader.get(ConfigReader.Settings.logSale) == 1;
        for (int i = 0; i < configReader.get(ConfigReader.Settings.dealers); i++) {
            if (log) {
                dealers.add(new Dealer(settings.dealerPeriod, carsStorageController, true));
            } else {
                dealers.add(new Dealer(settings.dealerPeriod, carsStorageController));
            }
        }
    }

    public void run() {

        for (AccessoriesSupplier supplier : accessoriesSuppliers) {
            supplier.start();
        }

        bodiesSupplier.start();
        enginesSupplier.start();


        for (Dealer dealer : dealers) {
            dealer.start();
        }

        carsStorageController.start();
    }

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        Settings settings = new Settings(5000, 2000, 3000, 10000);
        Main factory = new Main(configReader, settings);
        FactoryPanel factoryPanel = new FactoryPanel(factory);
        factory.setSize(new Dimension(1080, 720));
        factory.setTitle("factory");
        factory.getContentPane().add(factoryPanel);
        factory.setVisible(true);
        factory.setDefaultCloseOperation(EXIT_ON_CLOSE);
        factory.setResizable(false);
        settings.addObserver(factory);
        factory.run();
    }

    @Override
    public void notify(Event event) {
        if (event instanceof AccessoriesSupplierEvent) {
            this.settings.accessoriesSupplierSpeed = event.value * 1000;
            for (AccessoriesSupplier supplier : accessoriesSuppliers) {
                supplier.setPeriod(this.settings.accessoriesSupplierSpeed);
            }
        } else if (event instanceof BodySupplierEvent) {
            this.settings.bodiesSupplierSpeed = event.value * 1000;
            bodiesSupplier.setPeriod(this.settings.bodiesSupplierSpeed);
        } else if (event instanceof DealerEvent) {
            this.settings.dealerPeriod = event.value * 1000;
            for (Dealer dealer : dealers) {
                dealer.setSpeed(this.settings.dealerPeriod);
            }
        } else if (event instanceof EngineSupplierEvent) {
            this.settings.enginesSupplierSpeed = event.value * 1000;
            enginesSupplier.setPeriod(this.settings.enginesSupplierSpeed);
        }
    }
}