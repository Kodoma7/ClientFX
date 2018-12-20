package com.kodoma.controller;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ValueHolder class.
 * Created on 17.12.2018.
 * @author Kodoma.
 */
public class ValueHolder<V> {

    public static final ValueHolder HOLDER = new ValueHolder<>();
    private static final Map<String, Object> MAP = Maps.newHashMap();

    private ValueHolder() {
    }

    public V getValue(final String name) {
        return (V)MAP.get(name);
    }

    public void addValue(final String name, final Object value) {
        MAP.put(name, value);
    }
}
