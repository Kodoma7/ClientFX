package com.kodoma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML private TextArea textArea;

    @FXML private TextArea resultText;

    @FXML private ImageView switchOn;

    @FXML private ImageView switchOff;

    private final Model model = Model.getInstance();

    public void pressButton(ActionEvent event) {
        final String text = textArea.getText();
        model.setSdp(text);
        System.out.println(text);
    }

    public void switchOn() {
        switchOn.setVisible(true);
        switchOff.setVisible(false);
        model.setEnabled(true);
        System.out.println("turn on");
    }

    public void switchOff() {
        switchOn.setVisible(false);
        switchOff.setVisible(true);
        model.setEnabled(false);
        System.out.println("turn off");
    }
}
