package com.kodoma.parser.values;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kodoma.parser.util.Util;

import java.util.regex.Pattern;

/**
 * SdpValue class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public abstract class SdpValue implements Cloneable {

    static final Pattern FIRST_VALUE_PATTERN = Pattern.compile("(.*?)\\s");
    static final Pattern SECOND_VALUE_PATTERN = Pattern.compile(".*?\\s(.*?)\\s");
    static final Pattern THIRD_VALUE_PATTERN = Pattern.compile(".*?\\s.*?\\s(.*?)\\s");

    public abstract SdpValue fill(final String raw);

    @Override
    public SdpValue clone() throws CloneNotSupportedException {
        return (SdpValue)super.clone();
    }

    @Override
    public String toString() {
        try {
            return Util.MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
