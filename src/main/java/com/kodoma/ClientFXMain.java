package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.controller.Controller;
import com.kodoma.controller.ValueHolder;
import com.kodoma.messenger.FXMessenger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.eclipse.fx.ui.controls.styledtext.*;

public class ClientFXMain extends Application {

    private FXWebSocketClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AnchorPane root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
        final Scene scene = new Scene(root, 1000, 700);
        final ObservableList<String> rootStylesheets = root.getStylesheets();
        final ObservableList<String> sceneStylesheets = scene.getStylesheets();
        final StyledTextArea textArea = new StyledTextArea();
        final Controller controller = ValueHolder.HOLDER.getValue("controller");

        textArea.setContent(new StyledTextAreaContent());

        controller.setClient(client);
        controller.setTextArea(textArea);

        textArea.heightProperty().addListener((observable, oldValue, newValue) -> {
            root.heightProperty().add(newValue.doubleValue());
        });

        StyledTextContent.TextChangeListener modifiedListener = new StyledTextContent.TextChangeListener() {

            @Override
            public void textChanged(TextChangedEvent event) {
                System.out.println();
            }

            @Override
            public void textSet(TextChangedEvent event) {
                System.out.println();
            }

            @Override
            public void textChanging(TextChangingEvent event) {
                System.out.println();
            }
        };

        textArea.editableProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println();
            }
        });

        textArea.contentProperty().addListener(new ChangeListener<StyledTextContent>() {
            @Override
            public void changed(ObservableValue<? extends StyledTextContent> observable,
                                StyledTextContent oldValue, StyledTextContent newValue) {
                if (oldValue != null)
                    oldValue.removeTextChangeListener(modifiedListener);
                if (newValue != null)
                    newValue.addTextChangeListener(modifiedListener);
            }
        });

        textArea.getContent().addTextChangeListener(new StyledTextContent.TextChangeListener() {

            @Override
            public void textChanged(TextChangedEvent textChangedEvent) {
                    System.out.println();
            }

            @Override
            public void textSet(TextChangedEvent textChangedEvent) {
                System.out.println();
            }

            @Override
            public void textChanging(TextChangingEvent textChangingEvent) {
                System.out.println();
            }
        });

        textArea.caretOffsetProperty().addListener((ChangeListener)(observable, oldValue, newValue) -> {
            System.out.println();
        });

        textArea.contentProperty().addListener((ChangeListener)(observable, oldValue, newValue) -> {
            System.out.println();
        });

        textArea.addEventHandler(EventType.ROOT, (EventHandler)event -> {
            if ("VERIFY".equals( event.getEventType().getName())) {
                final KeyCode code = ((VerifyEvent)event).getCode();
                if (KeyCode.DELETE == code || KeyCode.BACK_SPACE == code) {
                    textArea.setStyleRange(new StyleRange(""));
                }
            }
        });

        textArea.getStylesheets().add("/static/css/styled_text_area.css");
        // org.eclipse.fx.ui.controls.styledtext.VerifyEvent[source=StyledTextArea@1d57d770[styleClass=styled-text-area]]
        // ((StyledTextArea)event.getTarget()).getContent().setText("");
        root.getChildren().add(0, textArea);

        AnchorPane.setLeftAnchor(textArea, 0d);
        AnchorPane.setRightAnchor(textArea, 0d);
        AnchorPane.setTopAnchor(textArea, 0d);
        AnchorPane.setBottomAnchor(textArea, 0d);

        rootStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        rootStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        sceneStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        sceneStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        primaryStage.setTitle("ClientFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        client = FXWebSocketClient.getClient("wss://192.168.56.2:8443/restservice/fxRemote");

        FXMessenger.getInstance().setClient(client);
        client.start();
        super.init();
    }

    @Override
    public void stop() throws Exception {
        client.stop();
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
