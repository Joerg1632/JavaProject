package org.nsu;

import org.nsu.service.Observable;
import org.nsu.service.Observer;

public class Settings extends Observable implements Observer {
    public int accessoriesSupplierSpeed;
    public int bodiesSupplierSpeed;
    public int enginesSupplierSpeed;
    public int dealerPeriod;

    public Settings(int accessoriesSupplierSpeed, int bodiesSupplierSpeed, int enginesSupplierSpeed, int dealerPeriod) {
        this.accessoriesSupplierSpeed = accessoriesSupplierSpeed;
        this.bodiesSupplierSpeed = bodiesSupplierSpeed;
        this.dealerPeriod = dealerPeriod;
        this.enginesSupplierSpeed = enginesSupplierSpeed;
    }
}