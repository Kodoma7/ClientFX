package com.kodoma;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Observable;

/**
 * Model class.
 * Created on 30.09.2018.
 * @author Kodoma.
 */
public class Model extends Observable {
    private static final Model INSTANCE = new Model();
    private boolean isEnabled;
    private String sdp;

    public static Model getInstance() {
        return INSTANCE;
    }

    public String generateSdp(final String origSdp) {
        final HashSet<String> origSetSdp = Sets.newHashSet(origSdp.split("/n"));
        final HashSet<String> setSdp = Sets.newHashSet(sdp.split("/n"));
        final StringBuilder sb = new StringBuilder();

        origSetSdp.addAll(setSdp);
        origSetSdp.stream().map(s -> s.concat("\n")).forEach(sb::append);

        return sb.toString();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    public void printLog(String text) {
        setChanged();
        notifyObservers(text);
    }
}
