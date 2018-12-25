package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.controller.Controller;
import com.kodoma.controller.ValueHolder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFXMain extends Application {

    private FXWebSocketClient client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AnchorPane root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
        final Scene scene = new Scene(root, 1000, 700);
        final ObservableList<String> rootStylesheets = root.getStylesheets();
        final ObservableList<String> sceneStylesheets = scene.getStylesheets();
        final String stylesheet = getClass().getResource("/static/css/java-keywords.css").toExternalForm();
        final CodeArea codeArea = new CodeArea();
        final Controller controller = (Controller)ValueHolder.HOLDER.getValue("controller");

        controller.setClient(client);

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.getStylesheets().add(stylesheet);
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        //codeArea.replaceText(0, 0, "AAA");
        codeArea.setAutoScrollOnDragDesired(true);

        root.getChildren().add(codeArea);

        AnchorPane.setBottomAnchor(codeArea, 0.0);
        AnchorPane.setTopAnchor(codeArea, 0.0);
        AnchorPane.setLeftAnchor(codeArea, 0.0);
        AnchorPane.setRightAnchor(codeArea, 0.0);

        rootStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        rootStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        sceneStylesheets.add(getClass().getResource("/static/fonts/pattaya.ttf").toExternalForm());
        sceneStylesheets.add(getClass().getResource("/static/fonts/ubuntu.ttf").toExternalForm());

        primaryStage.setTitle("ClientFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        final Pattern pattern = Pattern.compile("(?<KEYWORD>\\b(INFO|WARNING)\\b)");

        final StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        final Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;

        while (matcher.find()) {
            String styleClass = "";

            if (matcher.group("KEYWORD") != null) {
                styleClass = "keyword";
            }
            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

        return spansBuilder.create();
    }

    @Override
    public void init() throws Exception {
        /*client = FXWebSocketClient.getClient("wss://192.168.127.237:8443/csa/fxRemote")
                                  .setUserName("dmsokol2")
                                  .setUserPassword("RAPtor1234");

        FXMessenger.getInstance().setClient(client);
        client.start();*/
        super.init();
    }

    @Override
    public void stop() throws Exception {
        //client.stop();
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
