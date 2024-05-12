package org.nsu.gui;

import org.nsu.Main;
import org.nsu.gui.listeners.EngineSliderListener;
import org.nsu.storage.StoragesMap;
import org.nsu.gui.listeners.AccessoriesSliderListener;
import org.nsu.gui.listeners.BodySliderListener;
import org.nsu.gui.listeners.DealerSliderListener;
import org.nsu.gui.listeners.EngineSliderListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class FactoryPanel extends JPanel implements ActionListener {
    private final Main factory;

    public FactoryPanel(Main factory) {
        this.factory = factory;
        this.setBackground(new Color(56, 56, 56));

        AccessoriesSliderListener l1 = new AccessoriesSliderListener();
        BodySliderListener l2 = new BodySliderListener();
        DealerSliderListener l3 = new DealerSliderListener();
        EngineSliderListener l4 = new EngineSliderListener();

        l1.addObserver(factory.settings);
        l2.addObserver(factory.settings);
        l3.addObserver(factory.settings);
        l4.addObserver(factory.settings);

        SliderWithLabel accessoriesSupplierSlider = new SliderWithLabel(l1, factory.accessoriesSuppliers.get(0).getPeriod() / 1000, "Accessories suppliers period (sec)");
        SliderWithLabel bodiesSupplierSlider = new SliderWithLabel(l2, factory.bodiesSupplier.getPeriod() / 1000, "Bodies supplier period (sec)");
        SliderWithLabel dealerSlider = new SliderWithLabel(l3, factory.dealers.get(0).getSpeed() / 1000, "Dealers period (sec)");
        SliderWithLabel enginesSupplierSlider = new SliderWithLabel(l4, factory.enginesSupplier.getPeriod() / 1000, "Engines supplier period (sec)");

        accessoriesSupplierSlider.setLocation(0, 0);
        bodiesSupplierSlider.setLocation(0, 50);
        dealerSlider.setLocation(0, 100);
        enginesSupplierSlider.setLocation(0, 150);

        this.add(accessoriesSupplierSlider);
        this.add(bodiesSupplierSlider);
        this.add(dealerSlider);
        this.add(enginesSupplierSlider);
        Timer timer = new Timer(100, this);
        timer.start();

    }

    private String getData() {
        String info = "";
        StoragesMap storages = factory.storages;
        if (storages != null) {
            info += "Accessories delivered: " + storages.get(StoragesMap.StoragesNames.accessories).getTotalProduced() + '\n';
            info += "Bodies delivered: " + storages.get(StoragesMap.StoragesNames.bodies).getTotalProduced() + '\n';
            info += "Engine delivered: " + storages.get(StoragesMap.StoragesNames.engines).getTotalProduced() + '\n';
            info += "Cars produced: " + storages.get(StoragesMap.StoragesNames.cars).getTotalProduced() + "\n\n";

            info += "Accessories in storage: " + storages.get(StoragesMap.StoragesNames.accessories).getCurrentSize() + '\n';
            info += "Bodies in storage: " + storages.get(StoragesMap.StoragesNames.bodies).getCurrentSize() + '\n';
            info += "Engines in storage: " + storages.get(StoragesMap.StoragesNames.engines).getCurrentSize() + '\n';
            info += "Cars in storage: " + storages.get(StoragesMap.StoragesNames.cars).getCurrentSize() + "\n";
        }
        return info;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        String data = getData();
        Graphics2D graphics2D = (Graphics2D) g;
        FontRenderContext frc = graphics2D.getFontRenderContext();
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        TextLayout layout = new TextLayout(data, font, frc);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font);
        String[] outputs = data.split("\n");
        for (int i = 0; i < outputs.length; i++) {
            graphics2D.drawString(outputs[i], 400, (int) (400 + i * layout.getBounds().getHeight() + 1));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}