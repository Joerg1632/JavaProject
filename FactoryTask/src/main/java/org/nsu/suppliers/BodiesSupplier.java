package org.nsu.suppliers;

import org.nsu.details.Body;
import org.nsu.details.Detail;
import org.nsu.util.IdsGenerator;
import org.nsu.storage.Storage;

public class BodiesSupplier extends Supplier {

    public BodiesSupplier(int period, Storage<Detail> storage) {
        super(period, storage);
    }

    @Override
    public void deliver() throws InterruptedException {
        sleep(period);
        storage.put(new Body(IdsGenerator.generateId()));
    }
}