package com.kodoma;

import com.kodoma.client.FXWebSocketClient;
import com.kodoma.controller.Controller;
import com.kodoma.controller.ValueHolder;
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
    private static final String SIP_USER_FROM = "((?<=From.{1,30})(<sip:.*@.*>))";
    private static final String SIP_USER_TO = "((?<=To.{1,30})(<sip:.*@.*>))";
    private static final String SIP_SDP = "\\b(\\w=)\\b";

    // <sip:skypeuser7715@csi71siteb.lab>
    // ((?<=From.{1,30})(<sip:.*@.*>))

    private static final Pattern PATTERN = Pattern.compile("(?<HEADER>" + SIP_HEADER + ")|(?<HEADLINE>" + SIP_HEADLINE +
                                                           ")|(?<USERFROM>" + SIP_USER_FROM + ")|(?<USERTO>" + SIP_USER_TO +
                                                           ")|(?<SDP>" + SIP_SDP + ")");
    private FXWebSocketClient client;

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
            codeArea.setStyleSpans(0, computeHighlighting(newText));
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
        int sdpStart = 0;

        while (matcher.find()) {
            String styleClass = "";

            if (matcher.group("HEADER") != null) {
                styleClass = "header";
            } else if (matcher.group("HEADLINE") != null) {
                styleClass = "headline";
            } else if (matcher.group("USERFROM") != null) {
                styleClass = "userfrom";
            } else if (matcher.group("USERTO") != null) {
                styleClass = "userto";
            } else if (matcher.group("SDP") != null) {
                styleClass = "sdp";
            }
            assert styleClass != null;

            spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());

            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);

        return spansBuilder.create();
    }

    private static void regexTest(final String text) {
        final Pattern pattern = Pattern.compile("(?<HEADER>\\b(?<=\n)([\\w|-]*?):)");
        final StringBuilder sb = new StringBuilder();
        final Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;

        while (matcher.find()) {
            String styleClass = "";

            if (matcher.group("HEADER") != null) {
                styleClass = "header";
            }
            assert styleClass != null;

            sb.append(text.substring(matcher.start(), matcher.end())).append("\n");
        }
        System.out.println(sb.toString());
    }

    @Override
    public void init() throws Exception {
/*        client = FXWebSocketClient.getClient("wss://135.60.86.6:8443/csa/fxRemote")
                                  .setUserName("17451231261@avayamcs.com")
                                  .setUserPassword("1234567");

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
