package com.kodoma.controller;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.model.Model;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML private TextArea textArea;
    @FXML private Button powerButton;
    @FXML private ImageView switchOn;
    @FXML private ImageView switchOff;

    private final Model model = new Model();

    private boolean powerButtonOn = true;

    public Controller() {
        model.addObserver(this);
    }

    @PostConstruct
    private void init() {
        ((FXWebSocketClient)ValueHolder.HOLDER.getValue()).sendMessage("client_enable", true);

        textArea.textProperty().addListener(
                (ChangeListener<Object>)(observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE));
    }

    @Override
    public void update(Observable o, Object arg) {
        printLog(arg.toString());
    }

    private void printLog(final String text) {
        Platform.runLater(() -> textArea.appendText(text + "\n") );
    }

    public void powerOn(ActionEvent event) {
        final FXWebSocketClient client = (FXWebSocketClient)ValueHolder.HOLDER.getValue();

        client.sendMessage("client_enable", true);
    }

    public void powerButtonClick(ActionEvent event) {
        final FXWebSocketClient client = (FXWebSocketClient)ValueHolder.HOLDER.getValue();

        if (powerButtonOn) {
            powerButton.setText("Off");
            powerButton.getStylesheets().add("/static/css/buttonOff.css");
            powerButton.getStylesheets().remove("/static/css/buttonOn.css");
            powerButtonOn = false;

            client.sendMessage("client_enable", false);
        } else {
            powerButton.setText("On");
            powerButton.getStylesheets().add("/static/css/buttonOn.css");
            powerButton.getStylesheets().remove("/static/css/buttonOff.css");
            powerButtonOn = true;

            client.sendMessage("client_enable", true);
        }
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
