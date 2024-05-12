package org.nsu.storage;

import org.nsu.details.Detail;
import java.util.HashMap;

public class StoragesMap {
    protected final HashMap<StoragesNames, Storage<Detail>> storages;

    public enum StoragesNames {
        accessories(AccessoriesStorage.class.getName()),
        engines(EnginesStorage.class.getName()),
        bodies(BodiesStorage.class.getName()),
        cars(CarsStorage.class.getName());

        private final String name;
        StoragesNames(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public StoragesMap(EnginesStorage enginesStorage, BodiesStorage bodiesStorage, AccessoriesStorage accessoriesStorage, CarsStorage carsStorage) {
        this.storages = new HashMap<>();
        this.storages.put(StoragesNames.accessories, accessoriesStorage);
        this.storages.put(StoragesNames.engines, enginesStorage);
        this.storages.put(StoragesNames.bodies, bodiesStorage);
        this.storages.put(StoragesNames.cars, carsStorage);
    }

    public Storage<Detail> get(StoragesNames name) {
        return storages.getOrDefault(name, null);
    }
}