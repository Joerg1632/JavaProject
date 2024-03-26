package org.nsu.controller;
import org.nsu.model.Model;
import org.nsu.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.getStartButton().addActionListener(new StartButtonListener());
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startGame();
        }
    }

    private void startGame() {
        JOptionPane.showMessageDialog(null, "Игра началась!");
    }
}


