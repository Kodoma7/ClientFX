package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.messenger.FXMessenger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientFXMain extends Application {

    private FXWebSocketClient client;

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        final Scene scene = new Scene(root, 1000, 689);

        root.getStylesheets().add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());

        primaryStage.setTitle("ClientFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
        client.start();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
