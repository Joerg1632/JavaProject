package org.nsu.details;

import org.nsu.util.IdsGenerator;

public class Car extends Detail {
    protected final Body body;
    protected final Engine engine;
    protected final Accessory accessory;

    public Car(Body body, Engine engine, Accessory accessory) {
        super(IdsGenerator.generateId());
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    public Body getBody() {
        return body;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public Engine getEngine() {
        return engine;
    }

}
