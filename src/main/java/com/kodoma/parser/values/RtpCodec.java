package com.kodoma.parser.values;

import java.util.regex.Pattern;

import static com.kodoma.parser.util.Util.getAttribute;

/**
 * RtpCodec class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class RtpCodec implements SdpValue {

    private String payloadType;

    private String encodingName;

    private String clockRate;

    @Override
    public SdpValue fill(final String raw) {
        this.payloadType = getAttribute(raw, Pattern.compile("(.*?)\\s"));
        this.encodingName = getAttribute(raw, Pattern.compile(".*?\\s(.*?)/\\d"));
        this.clockRate = getAttribute(raw, Pattern.compile(".*?\\s.*?/(.*?)$"));
        return this;
    }

    @Override
    public SdpValue clone() throws CloneNotSupportedException {
        return (SdpValue)super.clone();
    }

    public String getPayloadType() {
        return payloadType;
    }

    public RtpCodec setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getEncodingName() {
        return encodingName;
    }

    public RtpCodec setEncodingName(String encodingName) {
        this.encodingName = encodingName;
        return this;
    }

    public String getClockRate() {
        return clockRate;
    }

    public RtpCodec setClockRate(String clockRate) {
        this.clockRate = clockRate;
        return this;
    }

    @Override
    public String toString() {
        return "RtpCodec{" +
               "payloadType='" + payloadType + '\'' +
               ", encodingName='" + encodingName + '\'' +
               ", clockRate='" + clockRate + '\'' +
               '}';
    }
}
