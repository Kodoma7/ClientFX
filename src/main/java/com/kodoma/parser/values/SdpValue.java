package com.kodoma.parser.values;

/**
 * SdpValue class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public interface SdpValue extends Cloneable {

    SdpValue fill(final String raw);

    SdpValue clone() throws CloneNotSupportedException;
}
