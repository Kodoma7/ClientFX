package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.messenger.FXMessenger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientFXMain extends Application {

    private FXWebSocketClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
        final Scene scene = new Scene(root, 1000, 689);
        final ObservableList<String> rootStylesheets = root.getStylesheets();
        final ObservableList<String> sceneStylesheets = scene.getStylesheets();

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
        client = FXWebSocketClient.getClient("wss://192.168.56.2:8443/restservice/fxRemote")
                                  .setUserName("...")
                                  .setUserPassword("...");

        FXMessenger.getInstance().setClient(client);
        client.start();
        super.init();
    }

    @Override
    public void stop() throws Exception {
        client.stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
