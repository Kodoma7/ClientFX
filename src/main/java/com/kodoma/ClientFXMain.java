package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.controller.FXChangeListener;
import com.kodoma.controller.ValueHolder;
import com.kodoma.messenger.FXMessenger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class ClientFXMain extends Application {

    private FXWebSocketClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
        final Scene scene = new Scene(root, 1000, 700);
        final ObservableList<String> rootStylesheets = root.getStylesheets();
        final ObservableList<String> sceneStylesheets = scene.getStylesheets();
        final CodeArea codeArea = new CodeArea();
        final FXChangeListener listener = new FXChangeListener(codeArea);

        rootStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        rootStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        sceneStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        sceneStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        ValueHolder.HOLDER.addValue("change_listener", listener);

        codeArea.textProperty().addListener(listener);
        codeArea.replaceText(0, 0, "");

        primaryStage.setTitle("ClientFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        client = FXWebSocketClient.getClient("wss://192.168.127.237:8443/csa/fxRemote")
                                  .setUserName("dmsokol2")
                                  .setUserPassword("RAPtor1234");

        FXMessenger.getInstance().setClient(client);
        client.start();

        ValueHolder.HOLDER.addValue("client", client);
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
