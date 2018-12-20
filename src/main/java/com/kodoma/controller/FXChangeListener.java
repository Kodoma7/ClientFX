package com.kodoma.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.fxmisc.richtext.CodeArea;

/**
 * FXChangeListener class.
 * Created on 20.12.2018.
 * @author dmsokol.
 */
public class FXChangeListener implements ChangeListener {

    private CodeArea codeArea;

    public FXChangeListener(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

    }

    public void addText(final String text) {

    }
}
