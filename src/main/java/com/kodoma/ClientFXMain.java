package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.constans.StyleClass;
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
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFXMain extends Application {

    private static final String[] SIP_HEADLINES = new String[] {"INVITE.*SIP/2.0", "NEGOTIATE.*SIP/2.0", "SIP/2.0 180 Ringing",
                                                                "SIP/2.0 200 OK", "ACK.*SIP/2.0", "BYE.*SIP/2.0", "SUBSCRIBE.*SIP/2.0"};

    private static final String SIP_HEADLINE = "\\b(" + String.join("|", SIP_HEADLINES) + ")\\b";
    private static final String SIP_HEADER = "\\b(?<=\n)([\\w|-]*?):";
    private static final String SIP_USER_FROM = "((?<=From.{1,40})(<sip:.*@.*>))";
    private static final String SIP_USER_TO = "((?<=To.{1,40})(<sip:.*@.*>))";
    private static final String SIP_SDP = "\\b(\\w=)\\b";

    private static final Pattern PATTERN = Pattern.compile("(?<header>" + SIP_HEADER + ")|(?<headline>" + SIP_HEADLINE +
                                                           ")|(?<userfrom>" + SIP_USER_FROM + ")|(?<userto>" + SIP_USER_TO +
                                                           ")|(?<sdp>" + SIP_SDP + ")");
    private FXWebSocketClient client;
    private static boolean styleMatch;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AnchorPane root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
        final Scene scene = new Scene(root, 1000, 700);
        final ObservableList<String> rootStylesheets = root.getStylesheets();
        final ObservableList<String> sceneStylesheets = scene.getStylesheets();
        final CodeArea codeArea = new CodeArea();
        final Controller controller = (Controller)ValueHolder.HOLDER.getValue("controller");
        final VirtualizedScrollPane<CodeArea> scrollPane = new VirtualizedScrollPane<>(codeArea);

        controller.setClient(client);
        controller.setCodeArea(codeArea);

        scrollPane.getStylesheets().add("/static/css/scroll_pane.css");

        codeArea.getStylesheets().clear();
        codeArea.getStylesheets().add("/static/css/sip-highlighting.css");
        codeArea.getStylesheets().add("/static/css/code_area.css");
        codeArea.getStylesheets().add("/static/css/styled-text-area.css");

        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() <= newText.length()) {
                codeArea.setStyleSpans(oldText.length(), computeHighlighting(newText.substring(oldText.length())));
            }
            if (!styleMatch) {
                codeArea.setStyleSpans(0, computeHighlighting(newText));
            }
        });

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
        final StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        final Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        styleMatch = false;

        while (matcher.find()) {
            styleMatch = true;
            String styleClass = "";

            if (matcher.group(StyleClass.HEADER) != null) {
                styleClass = StyleClass.HEADER;
            } else if (matcher.group(StyleClass.HEAD_LINE) != null) {
                styleClass = StyleClass.HEAD_LINE;
            } else if (matcher.group(StyleClass.USER_FROM) != null) {
                styleClass = StyleClass.USER_FROM;
            } else if (matcher.group(StyleClass.USER_TO) != null) {
                styleClass = StyleClass.USER_TO;
            } else if (matcher.group(StyleClass.SDP) != null) {
                styleClass = StyleClass.SDP;
            }
            spansBuilder.add(Collections.singleton(StyleClass.DEFAULT), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());

            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton(StyleClass.DEFAULT), text.length() - lastKwEnd);

        return spansBuilder.create();
    }

    @Override
    public void init() throws Exception {
        client = FXWebSocketClient.getClient("wss://192.168.127.237:8443/csa/fxRemote")
                                  .setUserName("")
                                  .setUserPassword("");

        FXMessenger.getInstance().setClient(client);

        client.start();
        client.sendMessage("client_enable", true);
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
