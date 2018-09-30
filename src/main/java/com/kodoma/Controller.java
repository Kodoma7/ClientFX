package com.kodoma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class Controller {

    @FXML private TextArea textArea;

    @FXML private TextArea resultText;

    @FXML private ImageView switchOn;

    @FXML private ImageView switchOff;

    private final SipHandler handler = new SipHandler();

    private boolean isEnabled;

    public void pressButton(ActionEvent event) {
        final String text = textArea.getText();
        if ("#close".equals(text)) {
            System.exit(0);
        }
        System.out.println(text);
        try {
            handler.handle(text);

        } catch (Exception e) {
            resultText.setText(Arrays.toString(e.getStackTrace()));
        }
    }

    public void switchOn() {
        switchOn.setVisible(true);
        switchOff.setVisible(false);
        isEnabled = true;
        System.out.println("Turn on");
    }

    public void switchOff() {
        switchOn.setVisible(false);
        switchOff.setVisible(true);
        isEnabled = false;
        System.out.println("Turn off");
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
