package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.controller.Controller;
import com.kodoma.controller.ValueHolder;
import com.kodoma.messenger.FXMessenger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
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
        final VirtualizedScrollPane<CodeArea> scrollPane = new VirtualizedScrollPane<>(codeArea);

        controller.setClient(client);
        controller.setCodeArea(codeArea);

        scrollPane.getStylesheets().add("/static/css/scroll_pane.css");

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.getStylesheets().add(stylesheet);
        codeArea.getStylesheets().add("/static/css/code_area.css");
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        codeArea.setAutoScrollOnDragDesired(true);

        root.getChildren().add(0, scrollPane);

        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

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

            spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);

        return spansBuilder.create();
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
