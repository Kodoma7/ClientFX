package com.kodoma.controller;

/**
 * ValueHolder class.
 * Created on 17.12.2018.
 * @author Kodoma.
 */
public class ValueHolder<V> {

    public static final ValueHolder HOLDER = new ValueHolder<>();

    private ValueHolder() {
    }

    private V value;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
