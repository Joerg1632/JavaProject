package org.nsu.details;

abstract public class Detail {
    public final long id;

    public Detail(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
