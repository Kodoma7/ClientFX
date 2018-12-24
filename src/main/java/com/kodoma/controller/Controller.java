package com.kodoma.controller;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.model.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.eclipse.fx.ui.controls.styledtext.StyledTextArea;
import org.eclipse.fx.ui.controls.styledtext.StyledTextContent;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML private Button powerButton;

    private final Model model = new Model();

    private FXWebSocketClient client;
    private StyledTextArea textArea;
    private boolean powerButtonOn = true;
    private byte[] stringCache;

    public Controller() {
        model.addObserver(this);
        ValueHolder.HOLDER.addValue("controller", this);
    }

    @PostConstruct
    private void init() {
        client.sendMessage("client_enable", true);
    }

    @Override
    public void update(Observable o, Object arg) {
        printLog(arg.toString());
    }

    private void printLog(final String text) {
        Platform.runLater(() -> {
            final StyledTextContent content = textArea.getContent();
            final int to = content.getCharCount();

            stringCache = content.getTextRange(0, to).getBytes();
            content.setText(new String(stringCache).concat(text).concat("\n"));
        });
    }

    public void powerButtonClick(ActionEvent event) {
        textArea.setCaretOffset(100);

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

    public void setClient(FXWebSocketClient client) {
        this.client = client;
    }

    public void setTextArea(StyledTextArea textArea) {
        this.textArea = textArea;
    }
}
