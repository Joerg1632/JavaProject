package org.nsu.suppliers;

import org.nsu.details.Engine;
import org.nsu.details.Detail;
import org.nsu.util.IdsGenerator;
import org.nsu.storage.Storage;

public class EnginesSupplier extends Supplier {

    public EnginesSupplier(int period, Storage<Detail> storage) {
        super(period, storage);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        storage.put(new Engine(IdsGenerator.generateId()));
    }
}
