package com.kodoma.controller;

import com.kodoma.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML private TextArea textArea;

    @FXML private ImageView switchOn;

    @FXML private ImageView switchOff;

    private final Model model = new Model();

    public Controller() {
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        textArea.setText(textArea.getText().concat(arg.toString() + "\n"));
    }

    public void pressButton(ActionEvent event) {
        final String text = textArea.getText();
    }

    public void switchOn() {
        switchOn.setVisible(true);
        switchOff.setVisible(false);
        model.setEnabled(true);
    }

    public void switchOff() {
        switchOn.setVisible(false);
        switchOff.setVisible(true);
        model.setEnabled(false);
    }
}
