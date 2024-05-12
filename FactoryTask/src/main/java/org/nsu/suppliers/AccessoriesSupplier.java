package org.nsu.suppliers;

import org.nsu.details.Accessory;
import org.nsu.details.Detail;
import org.nsu.util.IdsGenerator;
import org.nsu.storage.Storage;

public class AccessoriesSupplier extends Supplier {

    public AccessoriesSupplier(int period, Storage<Detail> storage) {
        super(period, storage);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        storage.put(new Accessory(IdsGenerator.generateId()));
    }
}
