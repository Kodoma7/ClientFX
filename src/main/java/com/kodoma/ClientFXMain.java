package com.kodoma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientFXMain extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}