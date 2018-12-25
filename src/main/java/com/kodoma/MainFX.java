package com.kodoma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFX extends Application {
    private CodeArea codeArea = new CodeArea();
    private String exampleString = "This is a WARNING for an INFO! Please stay tuned";

    private static final String[] KEYWORDS = new String[] { "INFO", "WARNING" };
    /* This keyword could get another color by css */
    private static final String[] DEBUG_KEYWORD = new String[] { "DEBUG" };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"]|\\\")*\"";
    private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>"
                                                           + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")"
                                                           + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>"
                                                           + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN
                                                           + ")" + "|(?<STRING>" + STRING_PATTERN + ")");

    public static void main(String[] args) {
        launch(args);
        //regexTest("This is a WARNING for an INFO! Please stay tuned");
    }

    private static void regexTest(final String text) {
        // (?<KEYWORD>\b(INFO|WARNING)\b)|(?<PAREN>\(|\))|(?<BRACE>\{|\})|(?<BRACKET>\[|\])|(?<SEMICOLON>\;)|(?<STRING>"([^"]|\")*")

        final Matcher matcher = Pattern.compile("(?<KEYWORD>\\b(INFO|WARNING)\\b)").matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group("KEYWORD"));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        final String stylesheet = getClass().getResource("/static/css/java-keywords.css").toExternalForm();
        final Scene scene = new Scene(new StackPane(codeArea), 600, 400);

        primaryStage.setTitle("Richtext demo");

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.getStylesheets().add(stylesheet);

        codeArea.textProperty().addListener((obs, oldText, newText) -> codeArea.setStyleSpans(0, computeHighlighting(newText)));
        codeArea.replaceText(0, 0, exampleString);
        codeArea.setAutoScrollOnDragDesired(true);

        scene.getStylesheets().add(stylesheet);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * For Content highlighting
     */
    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        // (?<KEYWORD>\b(INFO|WARNING)\b)|(?<PAREN>\(|\))|(?<BRACE>\{|\})|(?<BRACKET>\[|\])|(?<SEMICOLON>\;)|(?<STRING>"([^"]|\")*")

        final StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        final Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;

        while (matcher.find()) {
            final String styleClass;

            if (matcher.group("KEYWORD") != null) {
                styleClass = "keyword";
            } else {
                if (matcher.group("PAREN") != null) {
                    styleClass = "paren";
                } else {
                    if (matcher.group("BRACE") != null) {
                        styleClass = "brace";
                    } else {
                        if (matcher.group("BRACKET") != null) {
                            styleClass = "bracket";
                        } else {
                            if (matcher.group("SEMICOLON") != null) {
                                styleClass = "semicolon";
                            } else {
                                if (matcher.group("STRING") != null) {
                                    styleClass = "string";
                                } else {
                                    styleClass = null;
                                }
                            }
                        }
                    }
                }
            }
            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

        return spansBuilder.create();
    }
}
