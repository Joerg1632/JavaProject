package org.nsu.gui.listeners;

import org.nsu.service.Observable;
import org.nsu.gui.events.EngineSupplierEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EngineSliderListener extends Observable implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        notify(new EngineSupplierEvent(slider.getValue()));
    }
}