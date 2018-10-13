package com.kodoma.parser.values;

/**
 * SdpValuesFactory class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class SdpValuesFactory {

    public static SdpValue getValue(final String raw, final SdpValue value) {
        return value.fill(raw);
    }
}
