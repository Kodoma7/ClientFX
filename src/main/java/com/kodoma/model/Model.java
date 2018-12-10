package com.kodoma.model;

import com.kodoma.messenger.FXMessenger;

import java.util.Observable;

/**
 * Model class.
 * Created on 30.09.2018.
 * @author Kodoma.
 */
public class Model extends Observable {

    private boolean isEnabled;

    public Model() {
        init();
    }

    private void init() {
        final FXMessenger.ClientConsumer consumer = new FXMessenger.ClientConsumer() {

            @Override
            public void onMessage(String message) {
                printLog(message);
            }
        };
        consumer.process();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void printLog(String text) {
        setChanged();
        notifyObservers(text);
    }
}
